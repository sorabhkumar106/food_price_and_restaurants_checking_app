package com.sorabh.foodkart.fragments.favorite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sorabh.foodkart.R
import com.sorabh.foodkart.adapter.FoodAdapter
import com.sorabh.foodkart.adapter.FoodViewHolder
import com.sorabh.foodkart.databinding.FragmentFavoriteBinding
import com.sorabh.foodkart.repository.FoodRepository
import com.sorabh.foodkart.util.DataX
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment(), FoodViewHolder.OnFavoriteButtonClicked {
    lateinit var favoriteBinding: FragmentFavoriteBinding
    private lateinit var repository: FoodRepository
    private lateinit var foodAdapter: FoodAdapter
    private var job = SupervisorJob()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        favoriteBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_favorite, container, false)

        //changing toolbar title
        changeToolbarTitle()

        // initializing repository
        repository = FoodRepository(activity as Context)

        //initializing adapter
        foodAdapter = FoodAdapter(this,activity as Context)


        CoroutineScope(job + Dispatchers.IO).launch {
            var list = favoriteRestaurantsList()
            withContext(job + Dispatchers.Main) {
                with(favoriteBinding.favoriteRecyclerView) {
                    layoutManager = LinearLayoutManager(activity as Context)
                    adapter = foodAdapter

                }
                Log.d("yp", list.toString())
                foodAdapter.updateFoodList(list,true)
                favoriteBinding.favoriteProgressBar.visibility = ProgressBar.INVISIBLE

            }
        }

        return favoriteBinding.root
    }

    // to change toolbar title
    private fun changeToolbarTitle(title: String = "Favorite Restaurants") {
        val toolbar: androidx.appcompat.widget.Toolbar? = activity?.findViewById(R.id.toolBar)
        toolbar?.title = title
    }

    private suspend fun favoriteRestaurantsList(): List<DataX>? = coroutineScope {
        val jobData = CoroutineScope(job + Dispatchers.IO).async {
            return@async repository.getRestaurantsData()
        }
        return@coroutineScope jobData.await()
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteBinding.unbind()
    }

    override fun onFavoriteButtonClicked(data: DataX) {
        var isInserted :Boolean
        CoroutineScope(job + Dispatchers.IO).launch {
            val obj = repository.getRestaurantData(data.id)
            isInserted = if (obj == null) {
                repository.insertRestaurantData(data)
                true
            } else {
                repository.deleteRestaurantData(data)
                false
            }
            withContext(Dispatchers.Main) {
                if (isInserted) {
                    Toast.makeText(activity, "Added to Your Favorite!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Deleted to Your Favorite!", Toast.LENGTH_SHORT).show()
                }
            }
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.frameLayout,FavoriteFragment())
                .commit()
        }
    }
}