package com.example.forecastapplication.data.repositories

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.forecastapplication.data.db.room.CityRoomDatabase
import com.example.forecastapplication.data.db.room.dao.CityDao
import com.example.forecastapplication.data.db.room.entity.City
import javax.inject.Inject

class CityRepository @Inject constructor(db: CityRoomDatabase) {
    private val mCityDao: CityDao = db.cityDao()
    val allCities: LiveData<List<City?>?>? = mCityDao.allCity


    suspend fun insert(city: City?) {
        mCityDao.insert(city)
    }

    fun deleteAll() {
        mCityDao.deleteAll()
    }

    suspend fun deleteCity(city: City?) {
        mCityDao.deleteCity(city)
    }

    suspend fun updateCity(city: City?) {
        mCityDao.update(city)
    }

    //------------------
    //------------------
/*    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: CityDao) :
        AsyncTask<City?, Void?, Void?>() {
        override fun doInBackground(vararg params: City?): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }


    }

    private class deleteAllCitysAsyncTask internal constructor(private val mAsyncTaskDao: CityDao) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg voids: Void?): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    private class deleteCityAsyncTask internal constructor(private val mAsyncTaskDao: CityDao) :
        AsyncTask<City?, Void?, Void?>() {
        override fun doInBackground(vararg params: City?): Void? {
            mAsyncTaskDao.deleteCity(params[0])
            return null
        }
    }

    private class updateCityAsyncTask internal constructor(private val mAsyncTaskDao: CityDao) :
        AsyncTask<City?, Void?, Void?>() {
        override fun doInBackground(vararg params: City?): Void? {
            mAsyncTaskDao.update(params[0])
            return null
        }
    }*/

}