package com.sorabh.foodkart.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sorabh.foodkart.util.DataX

@Database(entities = [DataX::class], version = 1)
abstract class RestaurantDatabase:RoomDatabase() {
    abstract fun getRestaurantsDao(): RestaurantsDAO

    companion object {
        var INSTANCE: RestaurantDatabase? = null
        private const val dbName = "RESTAURANTS_DB"
        fun getDBInstance(context: Context):RestaurantDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,RestaurantDatabase::class.java, dbName).build()
            }
            return INSTANCE!!
        }
    }
}