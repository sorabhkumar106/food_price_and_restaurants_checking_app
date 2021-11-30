package com.sorabh.foodkart.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sorabh.foodkart.util.DataX

@Dao
interface RestaurantsDAO {
    @Insert
    fun addRestaurant(data: DataX)

    @Delete
    fun deleteRestaurantData(data: DataX)

    @Query("Select * from restaurants_data")
    fun getRestaurantsData(): List<DataX>?

    @Query("select * from restaurants_data where id == :id")
    fun getRestaurantData(id: String):DataX?
}