package com.batzalcancia.auth.domain.repositories

import com.batzalcancia.auth.data.local.entities.User

interface UsersRepository {

    suspend fun registerUser(username: String, password: String, country: String)

    suspend fun login(username: String, password: String): User

}