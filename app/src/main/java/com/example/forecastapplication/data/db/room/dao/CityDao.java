package com.example.forecastapplication.data.db.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.forecastapplication.data.db.room.entity.City;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(City city);

    @Query("DELETE FROM city_table")
    void deleteAll();

    @Query("SELECT * from city_table ORDER BY id DESC")
    LiveData<List<City>> getAllCity();

    // This method is used to test if table has at least one data.
    @Query("SELECT * from city_table LIMIT 1")
    City[] getAnyCity();

    @Delete
    void deleteCity(City city);

    @Update
    void update(City... city);
}
