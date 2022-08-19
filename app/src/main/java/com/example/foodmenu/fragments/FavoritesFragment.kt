package com.example.foodmenu.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmenu.R
import com.example.foodmenu.activities.MainActivity
import com.example.foodmenu.adapters.FavoriteMealsAdapter
import com.example.foodmenu.databinding.FragmentFavoritesBinding
import com.example.foodmenu.viewModel.CategoryMealsViewModel
import com.example.foodmenu.viewModel.HomeViewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var favoritesAdapter:FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoriteMealsAdapter()
        homeViewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                homeViewModel.deleteMealFromDatabase(favoritesAdapter.differ.currentList[pos])

                val toast = Toast.makeText(view.context, "Meal deleted", Toast.LENGTH_LONG)
                toast.show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recViewFavorites)

    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoriteMealsAdapter()
        binding.recViewFavorites.apply {
            layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        homeViewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer{ meals ->
            meals.forEach { Log.d("test", it.idMeal) }
            favoritesAdapter.differ.submitList(meals)
        })
    }


}