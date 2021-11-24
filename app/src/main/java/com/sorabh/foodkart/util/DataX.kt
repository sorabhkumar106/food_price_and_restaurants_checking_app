package com.sorabh.foodkart.util

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Restaurants_Data")
data class DataX(
    @ColumnInfo(name = "Price")
    val cost_for_one: String,
    @PrimaryKey
    val id: String,
    val image_url: String,
    val name: String,
    val rating: String
)