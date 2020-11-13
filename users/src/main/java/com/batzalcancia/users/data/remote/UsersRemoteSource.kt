package com.batzalcancia.users.data.remote

import com.batzalcancia.users.data.entities.User
import retrofit2.Retrofit
import javax.inject.Inject

interface UsersRemoteSource {

    suspend fun getAllUsers(): List<User>

}