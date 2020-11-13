package com.batzalcancia.users.domain.repositories

import com.batzalcancia.users.data.entities.User

interface UsersRepository {

    suspend fun getAllUsers() : List<User>

}