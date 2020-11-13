package com.batzalcancia.users.domain.usecases

import com.batzalcancia.users.domain.repositories.UsersRepository
import javax.inject.Inject

class GetAllUsers @Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke() = usersRepository.getAllUsers()
}