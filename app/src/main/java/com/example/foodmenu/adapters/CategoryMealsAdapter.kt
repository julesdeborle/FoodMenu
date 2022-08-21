package com.example.foodmenu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodmenu.databinding.MealItemBinding
import com.example.foodmenu.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {
    private var mealsList: List<MealsByCategory> = ArrayList()
    lateinit var onItemClick: ((MealsByCategory) -> Unit)

    fun setMealsList(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        return CategoryMealsViewModel(
            MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
                .into(imgMealFromCategory)
            tvMealName.text = mealsList[position].strMeal
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    inner class CategoryMealsViewModel(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}