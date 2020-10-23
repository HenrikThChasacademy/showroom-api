package com.chasreact.shoowroom.router

import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class HealthCheckRouter {

    @FlowPreview
    @Bean
    fun healthCheckRoutes() = router {
        GET("/health") {_ -> ok().bodyValue("I'm alive")}
    }
}