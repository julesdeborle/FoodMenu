package com.example.foodmenu.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodmenu.R
import com.example.foodmenu.database.MealDatabase
import com.example.foodmenu.databinding.ActivityMealBinding
import com.example.foodmenu.fragments.HomeFragment
import com.example.foodmenu.pojo.Meal
import com.example.foodmenu.viewModel.MealViewModel
import com.example.foodmenu.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private var binding: ActivityMealBinding? = null
    private lateinit var mealViewModel: MealViewModel
    private lateinit var youtubeLink: String

    private val meal:Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal)

        val mealDatabase = MealDatabase.getInstance(context = this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealViewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getMealInfoFromIntent()
        setInformationInViews()

        mealViewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()

        onVideoImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding?.btnFavorite?.setOnClickListener {
            mealToStore?.let {
                mealViewModel.upsertMealToDatabase(it)
            }

        }
    }

    private fun onVideoImageClick() {
        binding?.imgYoutube?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToStore: Meal? = null

    //Category, Origin, Instructions instellen
    private fun observeMealDetailsLiveData() {
        mealViewModel.observeMealInfoLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal) {
                val meal = t
                mealToStore = meal

                binding?.tvCategoryInfo?.text = "Category: ${meal.strCategory}"
                binding?.tvContent?.text = meal.strInstructions
                binding?.tvOriginInfo?.text = "Origin: ${meal.strArea}"

                youtubeLink = meal.strYoutube as String
            }
        })
    }

    private fun setInformationInViews() {
        binding?.let {
            Glide.with(applicationContext)
                .load(mealThumb)
                .into(it.imgMealDetail)
        }

        binding?.collapsingToolbar?.title = mealName
        binding?.collapsingToolbar?.setExpandedTitleColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.white
            )
        )
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}