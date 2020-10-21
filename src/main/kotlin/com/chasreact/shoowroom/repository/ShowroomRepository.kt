package com.chasreact.shoowroom.repository;

import com.chasreact.shoowroom.model.Car
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ShowroomRepository : ReactiveMongoRepository<Car, String>