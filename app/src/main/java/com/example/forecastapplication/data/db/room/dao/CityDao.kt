package com.example.forecastapplication.data.db.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forecastapplication.data.db.room.entity.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City?)

    @Query("DELETE FROM city_table")
    fun deleteAll()

    @get:Query("SELECT * from city_table ORDER BY id DESC")
    val allCity: LiveData<List<City?>?>?

    // This method is used to test if table has at least one data.
    @get:Query("SELECT * from city_table LIMIT 1")
    val anyCity: Array<City?>?

    @Delete
    suspend fun deleteCity(city: City?)

    @Update
    suspend fun update(vararg city: City?)
}