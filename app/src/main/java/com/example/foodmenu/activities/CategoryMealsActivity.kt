package com.example.foodmenu.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodmenu.R
import com.example.foodmenu.adapters.CategoryMealsAdapter
import com.example.foodmenu.databinding.ActivityCategoryMealsBinding
import com.example.foodmenu.fragments.HomeFragment
import com.example.foodmenu.viewModel.CategoryMealsViewModel
import com.example.foodmenu.viewModel.HomeViewModel

class CategoryMealsActivity : AppCompatActivity() {
    var binding: ActivityCategoryMealsBinding? = null
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_meals)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            binding?.tvCategoryCount?.text = "Meals in category: ${mealsList.size.toString()}"
            categoryMealsAdapter.setMealsList(mealsList)
        })

        onCategoryMealClick()
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding?.recViewMealCategories?.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun onCategoryMealClick() {
        categoryMealsAdapter.onItemClick = { meal ->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}