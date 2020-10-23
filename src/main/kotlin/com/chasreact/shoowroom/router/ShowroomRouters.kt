package com.chasreact.shoowroom.router

import com.chasreact.shoowroom.handler.CarHandler
import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ShowroomRouters {

    @FlowPreview
    @Bean
    fun showroomRoutes(carHandler: CarHandler) = coRouter {
        GET("/car", carHandler::getCars)
        GET("/car/{id}", carHandler::getCarById)
        POST("/car", carHandler::saveCar)
        DELETE("/car/{id}", carHandler::deleteCar)
    }

}