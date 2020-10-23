package com.chasreact.shoowroom.handler

import com.chasreact.shoowroom.model.Car
import com.chasreact.shoowroom.repository.ShowroomRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class CarHandler (@Autowired var showroomRepository: ShowroomRepository){

    @FlowPreview
    suspend fun getCars(request: ServerRequest): ServerResponse =
        ServerResponse.ok().json().bodyAndAwait(showroomRepository.findAll().asFlow())

    suspend fun getCarById(request: ServerRequest): ServerResponse {
        val car: Deferred<Car?> = GlobalScope.async {
            showroomRepository.findById(request.pathVariable("id")).awaitFirstOrNull()
        }

        return car.await()?.let { ServerResponse.ok().bodyValueAndAwait(it) } ?:
            ServerResponse.notFound().buildAndAwait()
    }

    suspend fun saveCar(request: ServerRequest): ServerResponse {
        System.out.println(request)
        val car: Deferred<Car?> = GlobalScope.async {
            showroomRepository.save(request.awaitBody<Car>()).awaitFirstOrNull()
        }

        return car.await()?.let { ServerResponse.ok().bodyValueAndAwait(it) } ?:
            ServerResponse.notFound().buildAndAwait()
    }

    suspend fun deleteCar(request: ServerRequest): ServerResponse {

        val car: Deferred<Car?> = GlobalScope.async {
            showroomRepository.findById(request.pathVariable("id")).awaitFirstOrNull()
        }
        return car.await()?.let {
            val deletedCar: Deferred<Void?> = GlobalScope.async {
                    showroomRepository.delete(it).awaitFirstOrNull()
            }
            deletedCar.await().let { ServerResponse.ok().buildAndAwait() }
        } ?: ServerResponse.notFound().buildAndAwait()

    }

}