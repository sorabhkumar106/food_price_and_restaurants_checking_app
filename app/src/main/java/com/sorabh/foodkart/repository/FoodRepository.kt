package com.sorabh.foodkart.repository

import android.app.Activity
import android.content.Context
import com.sorabh.foodkart.database.RestaurantDatabase
import com.sorabh.foodkart.retrofit.NetworkAPI
import com.sorabh.foodkart.retrofit.NetworkData
import com.sorabh.foodkart.util.DataX


class FoodRepository(context: Context) {

    /*------------------------------ For Network Call -------------------------------*/
    private val accessAPI: NetworkAPI =
        NetworkData.getNetworkAccess().create(NetworkAPI::class.java)

    // getting data from API
    suspend fun getRestaurantsList(hashMap: HashMap<String, String>): List<DataX>? {
        return accessAPI.getRestaurantsList(hashMap).body()?.data?.data

    }


    /*------------------------------ For Local Database Call -------------------------------*/
    private val restaurantDao = RestaurantDatabase.getDBInstance(context).getRestaurantsDao()

    //adding data into room database
    fun insertRestaurantData(dataX: DataX) {
        restaurantDao.addRestaurant(dataX)
    }


    //accessing data from room database
    fun getRestaurantData(id: String): DataX? {
        return restaurantDao.getRestaurantData(id)
    }

    // deleting data  from room database
    fun deleteRestaurantData(dataX: DataX) {
        restaurantDao.deleteRestaurantData(dataX)
    }

    fun getRestaurantsData():List<DataX>?{
        return  restaurantDao.getRestaurantsData()
    }
}