package com.example.foodmenu.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodmenu.R
import com.example.foodmenu.database.MealDatabase
import com.example.foodmenu.databinding.ActivityMainBinding
import com.example.foodmenu.viewModel.HomeViewModel
import com.example.foodmenu.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    val homeViewModel: HomeViewModel by lazy { //Laatste lijn instantieerd homeViewModel aangezien "by lazy"
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val bottomNavigation = binding?.bottomNav?.let { findViewById<BottomNavigationView>(it.id) }
        val navController = Navigation.findNavController(this, R.id.mainNavHostFragment)

        if (bottomNavigation != null) {
            NavigationUI.setupWithNavController(bottomNavigation, navController)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}