package com.batzalcancia.auth.domain.usecases

import com.batzalcancia.auth.domain.repositories.UsersRepository
import javax.inject.Inject

class LoginUser @Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(username: String, password: String) =
        usersRepository.login(username, password).apply {
            //TODO: save sa local cache
        }
}