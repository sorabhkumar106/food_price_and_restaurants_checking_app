package com.sorabh.foodkart.util.binding_adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sorabh.foodkart.R
import com.squareup.picasso.Picasso

@BindingAdapter("imgSrcUrl")
fun ImageView.imgSrcUrl(url: String?) {
    if (url != null) {
        Picasso.with(this.context)
            .load(url)
            .error(R.drawable.pizza)
            .into(this)
    }
}

@BindingAdapter("txt")
fun TextView.txt(text: String?){
    val str = "Rs. "
    val txtPrice = str + text
    this.text = txtPrice
}