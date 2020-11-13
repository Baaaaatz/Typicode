package com.batzalcancia.auth.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.batzalcancia.auth.data.local.dao.UsersDao
import com.batzalcancia.auth.data.local.entities.User

@Database(entities = [User::class], version = 1)
abstract class UsersDatabase : RoomDatabase(){
    abstract fun getGithubUsersDao(): UsersDao
}