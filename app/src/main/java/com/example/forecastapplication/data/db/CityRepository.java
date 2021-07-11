package com.example.forecastapplication.data.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.example.forecastapplication.data.db.room.CityRoomDatabase;
import com.example.forecastapplication.data.db.room.dao.CityDao;
import com.example.forecastapplication.data.db.room.entity.City;

import java.util.List;

public class CityRepository {

    private CityDao mCityDao;
    private LiveData<List<City>> mAllCities;

    public CityRepository(Application application) {
        CityRoomDatabase db = CityRoomDatabase.getDatabase(application);
        mCityDao = db.cityDao();
        mAllCities = mCityDao.getAllCity();
    }

    //------------------
    //------------------


    public LiveData<List<City>> getAllCities() {
        return mAllCities;
    }

    public void insert (City city) {
        new insertAsyncTask(mCityDao).execute(city);
    }
    public void deleteAll()  {
        new deleteAllCitysAsyncTask(mCityDao).execute();
    }
    public void deleteCity(City city)  {
        new deleteCityAsyncTask(mCityDao).execute(city);
    }
    public void updateCity(City city)  {
        new updateCityAsyncTask(mCityDao).execute(city);
    }

    //------------------
    //------------------

    private static class insertAsyncTask extends AsyncTask<City, Void, Void> {

        private CityDao mAsyncTaskDao;

        insertAsyncTask(CityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final City... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllCitysAsyncTask extends AsyncTask<Void, Void, Void> {
        private CityDao mAsyncTaskDao;

        deleteAllCitysAsyncTask(CityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteCityAsyncTask extends AsyncTask<City, Void, Void> {
        private CityDao mAsyncTaskDao;

        deleteCityAsyncTask(CityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final City... params) {
            mAsyncTaskDao.deleteCity(params[0]);
            return null;
        }
    }

    private static class updateCityAsyncTask extends AsyncTask<City, Void, Void> {
        private CityDao mAsyncTaskDao;

        updateCityAsyncTask(CityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final City... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
