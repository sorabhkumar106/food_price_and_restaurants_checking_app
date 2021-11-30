package com.sorabh.foodkart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sorabh.foodkart.R
import com.sorabh.foodkart.databinding.FavoriteFoodLayoutBinding
import com.sorabh.foodkart.repository.FoodRepository
import com.sorabh.foodkart.util.DataX
import com.sorabh.foodkart.util.RestaurantDiffUtil
import kotlinx.coroutines.*

class FoodAdapter(val listener: FoodViewHolder.OnFavoriteButtonClicked,val context: Context) :
    RecyclerView.Adapter<FoodViewHolder>() {

    private var foodList: List<DataX>? = ArrayList()
    private var isOffline = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
        FoodViewHolder.form(parent)

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) =
        holder.bind(foodList!![position], listener, isOffline,this.context)

    override fun getItemCount(): Int = foodList!!.size

    fun updateFoodList(list: List<DataX>?, isOffline: Boolean = false) {
        this.isOffline = isOffline
        val diffCallback = RestaurantDiffUtil(this.foodList!!, list!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.foodList = list
        diffResult.dispatchUpdatesTo(this)
    }


}

class FoodViewHolder constructor(private val binding: FavoriteFoodLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currentDataX: DataX, listener: OnFavoriteButtonClicked, isOffline: Boolean,context: Context) {
        binding.data = currentDataX
        binding.listener = listener
        if (isOffline) {
            binding.btnFoodFavorite.setImageResource(R.drawable.ic_favorite_first)
        }else{
            CoroutineScope(Dispatchers.Main).launch {
                val isStore = isStore(currentDataX.id, context)
                if (isStore) {
                    binding.btnFoodFavorite.setImageResource(R.drawable.ic_favorite_first)
                }
            }

        }

        binding.executePendingBindings()
    }
    private suspend fun isStore(id:String, context: Context):Boolean = coroutineScope{
        val foodRepository = FoodRepository(context)
        val job= CoroutineScope(Dispatchers.IO).async {
           val data= foodRepository.getRestaurantData(id)
            return@async data != null
        }
        return@coroutineScope job.await()
    }
    companion object {
        fun form(parent: ViewGroup): FoodViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val favoriteFoodLayoutBinding = DataBindingUtil.inflate<FavoriteFoodLayoutBinding>(
                layoutInflater,
                R.layout.favorite_food_layout, parent, false
            )
            return FoodViewHolder(favoriteFoodLayoutBinding)
        }
    }

    interface OnFavoriteButtonClicked {
        fun onFavoriteButtonClicked(data: DataX)
    }
}