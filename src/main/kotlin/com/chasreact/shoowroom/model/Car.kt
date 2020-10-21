package com.chasreact.shoowroom.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Car(
        @Id val id: String? = null,
        val brand: String,
        val model: String,
        val year: String,
        val imagePath: String,
        val engine: String,
        val effect: String)
