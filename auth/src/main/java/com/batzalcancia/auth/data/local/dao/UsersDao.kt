package com.batzalcancia.auth.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batzalcancia.auth.data.local.entities.User

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun loginUser(username: String, password: String): List<User>

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>
}