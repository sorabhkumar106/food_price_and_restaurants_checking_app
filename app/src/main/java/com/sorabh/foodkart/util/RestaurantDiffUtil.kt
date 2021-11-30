package com.sorabh.foodkart.util

import androidx.recyclerview.widget.DiffUtil

class RestaurantDiffUtil(private val oldList: List<DataX>, private val newList: List<DataX>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return  oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  newList[newItemPosition].cost_for_one == oldList[oldItemPosition].cost_for_one &&
                newList[newItemPosition].id == oldList[oldItemPosition].id &&
                newList[newItemPosition].image_url == oldList[oldItemPosition].image_url &&
                newList[newItemPosition].rating == oldList[oldItemPosition].rating
    }
}

