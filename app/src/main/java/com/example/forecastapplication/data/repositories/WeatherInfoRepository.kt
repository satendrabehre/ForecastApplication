package com.example.forecastapplication.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.forecastapplication.data.response.currentweather.CurrentWeatherResponse
import com.example.forecastapplication.data.server.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherInfoRepository
@Inject constructor(val apiService: ApiService) {

    private val resultLiveData: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    private val failLiveData: MutableLiveData<String> = MutableLiveData()

    fun getResultLiveData(): MutableLiveData<CurrentWeatherResponse> {
        return resultLiveData
    }

    fun getFailLiveData(): MutableLiveData<String> {
        return failLiveData
    }

    fun getCurrentWeather(q: String, unit: String, lang: String, appId: String) {
        try {
            val call = apiService.getCurrentWeather(q, unit, lang, appId)
            call.enqueue(object : Callback<CurrentWeatherResponse> {
                override fun onResponse(
                    call: Call<CurrentWeatherResponse>,
                    response: Response<CurrentWeatherResponse>
                ) {
                    val responseCode = response.code()
                    Log.e(Companion.TAG, "response.code(): $responseCode")
                    Log.e(Companion.TAG, "response.message(): " + response.message())
                    if (responseCode == 200) {
                        val out = response.body()
                        //val result = out.toString()
                        Log.e(Companion.TAG, "response.body(): " + response.body())
                        //String message = result.get("message").getAsString();
                        if (response.isSuccessful) {
                            resultLiveData.value = out
                        } else {
                            failLiveData.value = "failed"
                        }
                    } else {
                        var error = response.message()
                        if (error.isEmpty()) {
                            error = "Response Code: $responseCode"
                        }
                        Log.e(Companion.TAG, error)
                        failLiveData.value = "failed"
                    }
                }

                override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                    val error = if (t.message != null) t.message else "no message"
                    Log.e(Companion.TAG, "error: $error")
                    failLiveData.value = error
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            failLiveData.value = "failed"
        }
    }

    companion object {
        private const val TAG = "WeatherInfoRepository"
    }

}