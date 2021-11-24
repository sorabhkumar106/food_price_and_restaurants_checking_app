package com.sorabh.foodkart.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sorabh.foodkart.util.DataX

@Dao
interface RestaurantsDAO {
    @Insert
    suspend fun addRestaurants(dataX: DataX)

    @Delete
    suspend fun deleteRestaurants(dataX: DataX)

    @Query("SELECT * FROM Restaurants_Data")
    suspend fun getRestaurantsList():List<DataX>
}