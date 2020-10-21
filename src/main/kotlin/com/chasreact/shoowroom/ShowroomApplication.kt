package com.chasreact.shoowroom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude= arrayOf(MongoReactiveDataAutoConfiguration::class))
class ShowroomApplication

fun main(args: Array<String>) {
	runApplication<ShowroomApplication>(*args)
}
