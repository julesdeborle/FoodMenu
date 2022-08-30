package com.example.foodmenu

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foodmenu.network.RetrofitInstance
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class APITest {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    @Test
    fun getRandomMeal(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val randomMeal = RetrofitInstance.api.getRandomMeal()
                    Log.println(1,"randomMealTest", randomMeal.toString())
                }catch (e: Exception){
                    println("An error occurred" + e.message)
                }
            }
        }
    }

    @Test
    fun getPopularItems(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val popularItems = RetrofitInstance.api.getPopularItems("Seafood")
                    Log.println(1,"getPopularItemsTest", popularItems.toString())
                }catch (e: Exception){
                    println("An error occurred" + e.message)
                }
            }
        }
    }

    @Test
    fun getMealsByCategory(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val mealsByCategory = RetrofitInstance.api.getMealsByCategory("chicken_breast")
                    Log.println(1,"getMealsByCategoryTest", mealsByCategory.toString())
                }catch (e: Exception){
                    println("An error occurred" + e.message)
                }
            }
        }
    }

    @Test
    fun getMealInfo(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val mealInfo = RetrofitInstance.api.getMealInfo("52772")
                    Log.println(1,"getMealInfoTest", mealInfo.toString())
                }catch (e: Exception){
                    println("An error occurred" + e.message)
                }
            }
        }
    }







}