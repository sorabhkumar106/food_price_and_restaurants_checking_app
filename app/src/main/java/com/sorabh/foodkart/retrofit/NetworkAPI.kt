package com.sorabh.foodkart.retrofit

import com.sorabh.foodkart.util.RestaurantsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap

// usl = http://13.235.250.119/v2/restaurants/fetch_result/
interface NetworkAPI {
    @GET("fetch_result/")
    fun getRestaurantsList(@HeaderMap hashMap: HashMap<String,String>):Response<RestaurantsData>
}