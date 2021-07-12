package com.example.forecastapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapplication.data.repositories.CityRepository
import com.example.forecastapplication.data.db.room.entity.City
import com.example.forecastapplication.data.repositories.WeatherInfoRepository
import com.example.forecastapplication.data.response.currentweather.CurrentWeatherResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val mRepository: CityRepository,
                    private val weatherInfoRepository: WeatherInfoRepository,
) : ViewModel() {

    private var mAllCities: LiveData<List<City?>?>? = null

    init {
        mAllCities = mRepository.allCities
    }

    fun getAllCities(): LiveData<List<City?>?>? {
        return mAllCities
    }

    fun insert(city: City?) {
        viewModelScope.launch {
            mRepository.insert(city)
        }
    }

    fun deleteAll() {
        mRepository.deleteAll()
    }

    fun deleteCity(city: City?) {
        viewModelScope.launch {
            mRepository.deleteCity(city)
        }
    }

    fun updateCity(city: City?) {
        viewModelScope.launch {
            mRepository.updateCity(city)
        }
    }

    //------------
    fun getResultLiveData(): MutableLiveData<CurrentWeatherResponse> {
        return weatherInfoRepository.getResultLiveData()
    }

    fun getFailLiveData(): MutableLiveData<String> {
        return weatherInfoRepository.getFailLiveData()
    }

    fun getCurrentWeather(q: String, unit: String, lang: String, appId: String) {
        weatherInfoRepository.getCurrentWeather(q, unit, lang, appId)
    }

}