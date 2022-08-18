package com.example.foodmenu.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodmenu.R
import com.example.foodmenu.databinding.ActivityMealBinding
import com.example.foodmenu.fragments.HomeFragment
import com.example.foodmenu.pojo.Meal
import com.example.foodmenu.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealViewModel: MealViewModel
    private lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInfoFromIntent()
        setInformationInViews()

        mealViewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()

        onVideoImageClick()
    }

    private fun onVideoImageClick() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    //Category, Origin, Instructions instellen
    private fun observeMealDetailsLiveData() {
        mealViewModel.observeMealInfoLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                val meal = t

                binding.tvCategoryInfo.text = "Category: ${meal!!.strCategory}"
                binding.tvContent.text = meal.strInstructions
                binding.tvOriginInfo.text = "Origin: ${meal!!.strArea}"

                youtubeLink = meal.strYoutube
            }
        })
    }

    private fun setInformationInViews() {
            Glide.with(applicationContext)
                .load(mealThumb)
                .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
}