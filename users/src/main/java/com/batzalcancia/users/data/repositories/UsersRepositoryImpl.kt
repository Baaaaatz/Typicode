package com.batzalcancia.users.data.repositories

import com.batzalcancia.users.data.entities.User
import com.batzalcancia.users.data.remote.UsersRemoteSource
import com.batzalcancia.users.domain.repositories.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersRemoteSource: UsersRemoteSource
) : UsersRepository {

    override suspend fun getAllUsers() = withContext(Dispatchers.IO) {
        usersRemoteSource.getAllUsers()
    }

}