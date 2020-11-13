package com.batzalcancia.users.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Geo(
        val lat: Double,
        val lng: Double
)