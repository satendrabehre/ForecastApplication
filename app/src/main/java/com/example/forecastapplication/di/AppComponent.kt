package com.example.forecastapplication.di

import com.example.forecastapplication.MyApplication
import com.example.forecastapplication.ui.home.HomeFragment
import com.google.android.gms.maps.MapFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: MyApplication?)
    fun inject(fragment: MapFragment?)
    fun inject(fragment: HomeFragment?)
}