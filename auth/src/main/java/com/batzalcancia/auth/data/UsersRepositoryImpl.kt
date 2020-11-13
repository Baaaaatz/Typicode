package com.batzalcancia.auth.data

import android.database.sqlite.SQLiteConstraintException
import com.batzalcancia.auth.data.local.dao.UsersDao
import com.batzalcancia.auth.data.local.entities.User
import com.batzalcancia.auth.domain.repositories.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(val usersDao: UsersDao) : UsersRepository {
    override suspend fun registerUser(
        username: String,
        password: String,
        country: String
    ) = withContext(Dispatchers.IO) {
        try {
            usersDao.registerUser(User(username = username, password = password, country = country))
        } catch (e: SQLiteConstraintException) {
            throw SQLiteConstraintException("Username is already taken.")
        }
    }

    override suspend fun login(
        username: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        if (usersDao.loginUser(username, password).isEmpty()) {
            throw error("No user found")
        } else {
            usersDao.loginUser(username, password).first()
        }
    }
}