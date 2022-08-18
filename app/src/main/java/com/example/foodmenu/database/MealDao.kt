package com.example.foodmenu.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.foodmenu.pojo.Meal


@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal:Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * from mealInformation")
    fun getAllMeals():LiveData<List<Meal>>
}
