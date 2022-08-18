package com.example.foodmenu.network

import com.example.foodmenu.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealInfo(@Query("i") id:String): Call<MealList>
}