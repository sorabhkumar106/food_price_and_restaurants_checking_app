package com.sorabh.foodkart.fragments.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sorabh.foodkart.R
import com.sorabh.foodkart.activities.MainActivity
import com.sorabh.foodkart.databinding.FragmentUserBinding

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    lateinit var binding: FragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user, container, false)

        //changing toolbar title
        changeToolbarTitle("User Profile")

        // adding data to views
        bindView()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun bindView() {
        val userData = arguments
        Log.d("yp", userData.toString())
        binding.txtName.text = userData?.get("name") as CharSequence?
        binding.txtPhone.text = userData?.get("phone") as CharSequence?
        binding.txtEmail.text = userData?.get("email") as CharSequence?
        binding.btnFollow.text = "Follow"
        binding.btnShare.text = "Share"
    }

    private fun changeToolbarTitle(title: String) {
        val toolbar: androidx.appcompat.widget.Toolbar? = activity?.findViewById(R.id.toolBar)
        toolbar?.title = title
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}