package com.example.foodmenu.bindingUtils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.foodmenu.pojo.Meal

//Adapter for imageURI
@BindingAdapter("mealImage")
fun ImageView.setMealImage2(meal : Meal?) {
    meal?.strMealThumb?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .into(this)
    }
}