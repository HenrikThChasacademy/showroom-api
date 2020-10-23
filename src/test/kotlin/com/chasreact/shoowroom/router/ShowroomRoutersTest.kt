package com.chasreact.shoowroom.router

import com.chasreact.shoowroom.handler.CarHandler
import com.chasreact.shoowroom.model.Car
import com.chasreact.shoowroom.repository.ShowroomRepository
import kotlinx.coroutines.FlowPreview
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@WebFluxTest(
        excludeAutoConfiguration = [ReactiveUserDetailsServiceAutoConfiguration::class, ReactiveSecurityAutoConfiguration::class]
)
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [CarHandler::class, ShowroomRouters::class])
class ShowroomRoutersTest {

    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var webClient: WebClient

    @MockBean
    private lateinit var showroomRepository: ShowroomRepository

    @FlowPreview
    @Test
    fun `get all cars`() {
        val car1 = Car("1", "Saab", "900 Turbo", "1992", "saab_900_turbo.jpg", "v4 2.0 turbo", "140 hp")
        val car2 = Car("2", "Volvo", "745 GL", "1988", "volvo_745_gl.jpg", "v4 2.0", "80 hp")
        val car3 = Car("3", "BMW", "525i", "2001", "bwm_525i.jpg", "v4 2.5", "180 hp")

        val spamFlux = Flux.just(car1, car2, car3)
        given(showroomRepository.findAll()).willReturn(spamFlux)
        client.get()
                .uri("/car")
                .exchange()
                .expectStatus()
                .isOk
                .expectBodyList<Car>()
                .contains(car1, car2, car3)

    }

    @FlowPreview
    @Test
    fun `get car by id`() {
        val car = Car("1", "Saab", "900 Turbo", "1992", "saab_900_turbo.jpg", "v4 2.0 turbo", "140 hp")
        val spamMono = Mono.just(car)

        given(showroomRepository.findById("1")).willReturn(spamMono)
        client.get()
                .uri("/car/1")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody<Car>()
                .isEqualTo(car)
    }

}