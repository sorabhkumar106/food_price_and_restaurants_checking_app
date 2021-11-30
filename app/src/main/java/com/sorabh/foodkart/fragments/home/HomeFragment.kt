package com.sorabh.foodkart.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sorabh.foodkart.R
import com.sorabh.foodkart.activities.MainActivity
import com.sorabh.foodkart.adapter.FoodAdapter
import com.sorabh.foodkart.adapter.FoodViewHolder
import com.sorabh.foodkart.databinding.FragmentHomeBinding
import com.sorabh.foodkart.fragments.favorite.FavoriteFragment
import com.sorabh.foodkart.repository.FoodRepository
import com.sorabh.foodkart.util.DataX
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : androidx.fragment.app.Fragment(), FoodViewHolder.OnFavoriteButtonClicked {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var repository: FoodRepository
    private val job = SupervisorJob()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)

        //changing title of toolbar
        changeToolbarTitle()

        //repository
        repository = FoodRepository(activity as Context)

        // initializing recyclerView
        val foodAdapter = FoodAdapter(this,activity as Context)
        with(homeBinding.recyclerView) {
            layoutManager = LinearLayoutManager(activity as Context)
            adapter = foodAdapter


            val scope = CoroutineScope(job + Dispatchers.Main)
            scope.launch {

                try {
                    val list = getFoodList()
                    foodAdapter.updateFoodList(list)
                    homeBinding.progressBar.visibility = ProgressBar.INVISIBLE
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            activity as Context,
                            "Can't Able to connect Internet",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }


        }
        return homeBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding.unbind()
    }

    private suspend fun getFoodList(): List<DataX>? = coroutineScope {
        val job = CoroutineScope(Dispatchers.IO).async {
            val header = HashMap<String, String>()
            header["Content-type"] = "application/json"
            header["token"] = "025c40375fadfd"
            return@async repository.getRestaurantsList(header)


        }
        return@coroutineScope job.await()
    }

    private fun changeToolbarTitle(title: String = "Restaurants List") {
        val toolbar: androidx.appcompat.widget.Toolbar? = activity?.findViewById(R.id.toolBar)
        toolbar?.title = title
    }

    //execute when favorite button clicked
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
        }
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.frameLayout, HomeFragment())
            .commit()
    }


}

