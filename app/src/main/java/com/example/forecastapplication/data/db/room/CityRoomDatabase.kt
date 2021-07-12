package com.example.forecastapplication.data.db.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.forecastapplication.data.db.room.dao.CityDao;
import com.example.forecastapplication.data.db.room.entity.City;


@Database(entities = {City.class}, version = 6, exportSchema = false)
public abstract class CityRoomDatabase extends RoomDatabase {

    private static CityRoomDatabase INSTANCE;
    public abstract CityDao cityDao();

    public static CityRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CityRoomDatabase.class) {
                if (INSTANCE == null) {
                   INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                           CityRoomDatabase.class, "city_database")
                           .fallbackToDestructiveMigration()
                           .addCallback(sRoomDatabaseCallback)
                           .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback =
            new Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CityDao mDao;
        String[] cities = {"Mumbai", "London", "Pune"};

        PopulateDbAsync(CityRoomDatabase db) {
            mDao = db.cityDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            // If we have no cities, then create the initial list of cities
            if (mDao.getAnyCity().length < 1) {
                for (int i = 0; i <= cities.length - 1; i++) {
                    City city = new City(cities[i]);
                    mDao.insert(city);
                }
            }
            return null;
        }
    }
}