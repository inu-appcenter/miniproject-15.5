package com.example.todolist.Module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://jeonga.na2ru2.me"

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit : Retrofit) : UserApi{
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTodoApi(retrofit: Retrofit) : TodoApi{
        return retrofit.create(TodoApi::class.java)
    }
}