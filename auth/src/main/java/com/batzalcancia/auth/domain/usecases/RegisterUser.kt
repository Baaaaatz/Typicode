package com.batzalcancia.auth.domain.usecases

import com.batzalcancia.auth.domain.repositories.UsersRepository
import javax.inject.Inject


class RegisterUser @Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(username: String, password: String, country: String) =
        usersRepository.registerUser(username, password, country)
}