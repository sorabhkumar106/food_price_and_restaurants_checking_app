package com.sorabh.foodkart.util

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RESTAURANTS_DATA" )
data class DataX(
    val cost_for_one: String,
    @PrimaryKey
    val id: String,
    val image_url: String,
    val name: String,
    val rating: String
)