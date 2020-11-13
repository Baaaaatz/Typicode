package com.batzalcancia.users.di

import com.batzalcancia.users.data.api.TypicodeUsersApi
import com.batzalcancia.users.data.remote.UsersRemoteSource
import com.batzalcancia.users.data.remote.UsersRemoteSourceImpl
import com.batzalcancia.users.data.repositories.UsersRepositoryImpl
import com.batzalcancia.users.domain.repositories.UsersRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
@OptIn(ExperimentalSerializationApi::class)
object UsersModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttpClient)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    fun providesTypicodeUsersApi(retrofit: Retrofit) = retrofit.create(TypicodeUsersApi::class.java)

    @Singleton
    @Provides
    fun providesUsersRemoteSource(api: TypicodeUsersApi): UsersRemoteSource = UsersRemoteSourceImpl(api)

    @Singleton
    @Provides
    fun providesUsersRepository(usersRemoteSource: UsersRemoteSource): UsersRepository = UsersRepositoryImpl(usersRemoteSource)

}
