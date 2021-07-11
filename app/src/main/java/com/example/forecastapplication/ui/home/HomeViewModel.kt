package com.example.forecastapplication.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.forecastapplication.data.db.CityRepository
import com.example.forecastapplication.data.db.room.entity.City

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepository: CityRepository? = null
    private var mAllCities: LiveData<List<City?>?>? = null

    init {
        mRepository =
            CityRepository(
                application
            )
        mAllCities = mRepository?.getAllCities()
    }

    fun getAllCities(): LiveData<List<City?>?>? {
        return mAllCities
    }

    fun insert(city: City?) {
        mRepository?.insert(city)
    }

    fun deleteAll() {
        mRepository?.deleteAll()
    }

    fun deleteCity(city: City?) {
        mRepository?.deleteCity(city)
    }

    fun updateCity(city: City?) {
        mRepository?.updateCity(city)
    }
}