package com.example.foodmenu.fragments


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodmenu.databinding.FragmentHomeBinding
import com.example.foodmenu.pojo.Meal
import com.example.foodmenu.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeViewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getRandomMeal()
        observeRandomMeal()

    }

    private fun observeRandomMeal() {
        homeViewModel.observeRandomMealLivedata().observe(viewLifecycleOwner
        ) { t -> Glide.with(this@HomeFragment).load(t!!.strMealThumb).into(binding.imgRndMeal) }
    }


}