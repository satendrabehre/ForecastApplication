package com.example.forecastapplication.data.repositories

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.forecastapplication.data.db.room.CityRoomDatabase
import com.example.forecastapplication.data.db.room.dao.CityDao
import com.example.forecastapplication.data.db.room.entity.City
import javax.inject.Inject

class CityRepository @Inject constructor(db: CityRoomDatabase) {
    private val mCityDao: CityDao
    val allCities: LiveData<List<City>>
    /*@Inject
    lateinit var db: CityRoomDatabase*/

    init {
        //val db = CityRoomDatabase.getDatabase(appContext)
        mCityDao = db.cityDao()
        allCities = mCityDao.allCity
    }


    fun insert(city: City?) {
        insertAsyncTask(mCityDao).execute(city)
    }

    fun deleteAll() {
        deleteAllCitysAsyncTask(mCityDao).execute()
    }

    fun deleteCity(city: City?) {
        deleteCityAsyncTask(mCityDao).execute(city)
    }

    fun updateCity(city: City?) {
        updateCityAsyncTask(mCityDao).execute(city)
    }

    //------------------
    //------------------
    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: CityDao) :
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
    }


}