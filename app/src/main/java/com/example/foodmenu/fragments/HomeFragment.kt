package com.example.foodmenu.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodmenu.R
import com.example.foodmenu.activities.CategoryMealsActivity
import com.example.foodmenu.activities.MainActivity
import com.example.foodmenu.adapters.CategoriesAdapter
import com.example.foodmenu.adapters.MostPopularAdapter
import com.example.foodmenu.databinding.FragmentHomeBinding
import com.example.foodmenu.pojo.MealsByCategory
import com.example.foodmenu.pojo.Meal
import com.example.foodmenu.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).homeViewModel
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeViewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealclick()

        homeViewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeViewModel.getCategories()
        observeCategoriesLiveData()

        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val action = HomeFragmentDirections.actionHomeFragmentToCategoryMealsActivity(category.strCategory)
            findNavController().navigate(action)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding?.recViewCategories?.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeViewModel.observeCategoriesLiveData()
            .observe(viewLifecycleOwner, Observer { categories ->
                categoriesAdapter.setCategoryList(categories)
            })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToMealActivity(meal.idMeal)
            findNavController().navigate(action)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding?.recViewPopularMeals?.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeViewModel.observePopularItemsLiveData().observe(
            viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealclick() {
        binding?.rndMealCard?.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToMealActivity(randomMeal.idMeal)
            findNavController().navigate(action)
        }
    }

    private fun observeRandomMeal() {
        homeViewModel.observeRandomMealLivedata().observe(viewLifecycleOwner)
        { meal ->
            binding?.let {
                Glide.with(this@HomeFragment)
                    .load(meal.strMealThumb)
                    .load(meal.strMealThumb)
                    .into(it.imgRndMeal)
            }

            this.randomMeal = meal
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}