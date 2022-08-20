package com.example.foodmenu.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodmenu.database.MealDatabase

class HomeViewModelFactory(
    private val mealDatabase: MealDatabase
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(mealDatabase) as T
    }
}