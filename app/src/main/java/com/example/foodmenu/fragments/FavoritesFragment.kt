package com.example.foodmenu.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmenu.R
import com.example.foodmenu.activities.MainActivity
import com.example.foodmenu.activities.MealActivity
import com.example.foodmenu.adapters.FavoriteMealsAdapter
import com.example.foodmenu.databinding.FragmentFavoritesBinding
import com.example.foodmenu.viewModel.HomeViewModel

class FavoritesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var favoritesAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoriteMealsAdapter()
        homeViewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()
        onFavoriteMealClick()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                homeViewModel.deleteMealFromDatabase(favoritesAdapter.differ.currentList[pos])

                val toast = Toast.makeText(view.context, "Meal deleted", Toast.LENGTH_LONG)
                toast.show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recViewFavorites)

    }

    private fun onFavoriteMealClick() {
        favoritesAdapter.onItemClick = { meal ->
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoriteMealsAdapter()
        binding.recViewFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        homeViewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer { meals ->
            meals.forEach { Log.d("test", it.idMeal) }
            favoritesAdapter.differ.submitList(meals)
        })
    }


}