package com.sorabh.foodkart.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sorabh.foodkart.util.DataX

@Database(entities = [DataX::class], version = 1)
abstract class RestaurantsDB : RoomDatabase() {
    abstract fun getRestaurantsDAO(): RestaurantsDAO

    companion object {
        private var INSTANCE: RestaurantsDB? = null
        private const val dbName = "RESTAURANTS_DATABASE"

        @Synchronized
        fun getRestaurantsDB(context: Context): RestaurantsDB {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, RestaurantsDB::class.java, dbName)
                    .build()
            }
            return INSTANCE!!
        }

    }
}