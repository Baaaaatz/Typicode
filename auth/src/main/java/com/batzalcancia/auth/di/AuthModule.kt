package com.batzalcancia.auth.di


import android.content.Context
import androidx.room.Room
import com.batzalcancia.auth.data.UsersRepositoryImpl
import com.batzalcancia.auth.data.local.dao.UsersDao
import com.batzalcancia.auth.data.local.database.UsersDatabase
import com.batzalcancia.auth.domain.repositories.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun providesUsersDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, UsersDatabase::class.java, "users").build()

    @Singleton
    @Provides
    fun providesUsersDao(database: UsersDatabase) = database.getGithubUsersDao()

    @Singleton
    @Provides
    fun providesUsersRepository(usersDao: UsersDao): UsersRepository = UsersRepositoryImpl(usersDao)
}