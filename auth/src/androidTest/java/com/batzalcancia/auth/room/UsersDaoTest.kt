package com.batzalcancia.auth.room

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.batzalcancia.auth.data.local.database.UsersDatabase
import com.batzalcancia.auth.data.local.entities.User
import com.batzalcancia.auth.testutils.BaseAndroidTest
import com.batzalcancia.auth.testutils.UsersFactory
import com.batzalcancia.auth.testutils.testUser
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersDaoTest : BaseAndroidTest() {
    private lateinit var database: UsersDatabase

    @Before
    fun initDb() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UsersDatabase::class.java
        ).build()

    }

    @Test
    fun registerUser_shouldInsertAUserToDatabase() = runBlocking {
        // Given
        val users = UsersFactory.makeUserList(10)
        users.forEach {
            database.getGithubUsersDao().registerUser(it)
        }

        // When
        database.getGithubUsersDao().registerUser(testUser)

        // Then
        assert(database.getGithubUsersDao().getAllUsers().size != users.size)
    }

    @Test
    fun loginUser_shouldReturnAUser() = runBlocking {
        // Given
        database.getGithubUsersDao().registerUser(testUser)

        // When
        val userDetails =
            database.getGithubUsersDao().loginUser(testUser.username, testUser.password).first()

        // Then
        assert(database.getGithubUsersDao().getAllUsers().any { userDetails.id == it.id })
    }

    @Test
    fun loginInvalidUser_shouldReturnError() = runBlocking {
        // Given
        val users = UsersFactory.makeUserList(10)
        users.forEach {
            database.getGithubUsersDao().registerUser(it)
        }

        // Then
        assert(database.getGithubUsersDao().loginUser(testUser.username, testUser.password).isEmpty())
    }

    @After
    fun closeDb() {
        database.close()
    }
}
