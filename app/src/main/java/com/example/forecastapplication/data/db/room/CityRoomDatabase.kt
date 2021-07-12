package com.example.forecastapplication.data.db.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.forecastapplication.data.db.room.dao.CityDao
import com.example.forecastapplication.data.db.room.entity.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [City::class], version = 6, exportSchema = false)
abstract class CityRoomDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        private var INSTANCE: CityRoomDatabase? = null
        fun getDatabase(context: Context): CityRoomDatabase {
            if (INSTANCE == null) {
                synchronized(CityRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CityRoomDatabase::class.java, "city_database"
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                //PopulateDbAsync(INSTANCE).execute()
                CoroutineScope(Dispatchers.IO).launch {
                    insertDummyData(INSTANCE)
                }
            }
        }


        /**
         * Populate the database in the background.
         */
        suspend fun insertDummyData(db: CityRoomDatabase?){
            val cities = arrayOf("Mumbai", "London", "Pune")
            val mDao: CityDao = db!!.cityDao()
            if (mDao.anyCity?.isEmpty() == true) {
                for (element in cities) {
                    val city = City(element)
                    mDao.insert(city);
                }
            }
        }
    }
}