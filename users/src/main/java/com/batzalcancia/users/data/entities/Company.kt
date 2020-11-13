package com.batzalcancia.users.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Company(
        val name: String,
        val catchPhrase: String,
        val bs: String
)