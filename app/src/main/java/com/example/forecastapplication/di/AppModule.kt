package com.example.forecastapplication.di

import android.content.Context
import android.content.SharedPreferences
import com.example.forecastapplication.MyApplication
import com.example.forecastapplication.data.db.room.CityRoomDatabase
import com.example.forecastapplication.data.server.ApiClient
import com.example.forecastapplication.data.server.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MyApplication) {
    @Provides
    @Singleton
    fun providesApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(ctx: Context): SharedPreferences {
        return ctx.getSharedPreferences("my_prefes_1", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesCityRoomDatabase(ctx: Context): CityRoomDatabase {
        return CityRoomDatabase.getDatabase(ctx)
    }

    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return ApiClient.client!!.create(ApiService::class.java)
    }
}