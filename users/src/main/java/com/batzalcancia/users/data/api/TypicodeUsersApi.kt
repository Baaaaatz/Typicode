package com.batzalcancia.users.data.api

import com.batzalcancia.users.data.entities.User
import retrofit2.http.GET

interface TypicodeUsersApi {

    @GET("users")
    suspend fun getAllTypicodeUsers(): List<User>

}