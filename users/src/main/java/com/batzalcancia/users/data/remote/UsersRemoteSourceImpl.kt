package com.batzalcancia.users.data.remote

import com.batzalcancia.users.data.api.TypicodeUsersApi
import com.batzalcancia.users.data.entities.User
import retrofit2.Retrofit
import javax.inject.Inject

class UsersRemoteSourceImpl @Inject constructor(
    private val api: TypicodeUsersApi
) : UsersRemoteSource {

    override suspend fun getAllUsers() = api.getAllTypicodeUsers()

}