package com.example.foodmenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodmenu.R
import com.example.foodmenu.activities.MainActivity
import com.example.foodmenu.adapters.CategoriesAdapter
import com.example.foodmenu.databinding.FragmentCategoriesBinding
import com.example.foodmenu.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {

    private var binding: FragmentCategoriesBinding? = null
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()

        prepareCategoriesRecyclerView()
        homeViewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()
    }

    private fun observeCategoriesLiveData() {
        homeViewModel.observeCategoriesLiveData()
            .observe(viewLifecycleOwner, Observer { categories ->
                categoriesAdapter.setCategoryList(categories)
            })
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding?.recViewCategories?.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategoryMealsActivity(category.strCategory)
            findNavController().navigate(action)
        }
    }

    private fun observeCategories() {
        homeViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { c ->
            categoriesAdapter.setCategoryList((c))
        })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding?.recViewCategories?.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}