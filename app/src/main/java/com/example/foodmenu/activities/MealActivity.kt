package com.example.foodmenu.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.example.foodmenu.R
import com.example.foodmenu.database.MealDatabase
import com.example.foodmenu.databinding.ActivityMealBinding
import com.example.foodmenu.pojo.Meal
import com.example.foodmenu.viewModel.MealViewModel
import com.example.foodmenu.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private val args by navArgs<MealActivityArgs>()

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel: MealViewModel
    private lateinit var youtubeLink: String

    private var meal: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)

        mealViewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
        mealViewModel.getMealDetail(args.mealId)

        binding.viewModel = mealViewModel
        binding.lifecycleOwner = this

        mealViewModel.observeMealInfoLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal) {
                youtubeLink = t.strYoutube as String
            }
        })

        onVideoImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnFavorite.setOnClickListener {
            meal?.let {
                mealViewModel.upsertMealToDatabase(it)
            }

        }
    }

    private fun onVideoImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

}