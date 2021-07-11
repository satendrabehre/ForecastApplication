package com.example.forecastapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapplication.data.repositories.CityRepository
import com.example.forecastapplication.data.repositories.WeatherInfoRepository
import com.example.forecastapplication.ui.home.HomeViewModel


import com.example.forecastapplication.ui.map.MapViewModel
import javax.inject.Inject


/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory(var cityRepository: CityRepository,
                       var weatherRepository: WeatherInfoRepository) : ViewModelProvider.Factory {



    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel() as T
        }  else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(cityRepository, weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}