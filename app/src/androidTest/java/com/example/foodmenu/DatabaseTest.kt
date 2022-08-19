package com.example.foodmenu

import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.foodmenu.database.MealDao
import com.example.foodmenu.database.MealDatabase
import com.example.foodmenu.pojo.Meal
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var mealDAO : MealDao
    private lateinit var mealDatabase: MealDatabase
//    private lateinit var allMeals : LiveData<List<Meal>>

    @Before
    fun createDatabase(){
//        uiScope.launch {
//            withContext(Dispatchers.IO){
//                try {
                    val context = InstrumentationRegistry.getInstrumentation().targetContext

                    mealDatabase = Room.inMemoryDatabaseBuilder(context, MealDatabase::class.java).allowMainThreadQueries().build()
                    mealDAO = mealDatabase.mealDao()

//                    val meal1 = Meal(idMeal = "1", strMeal = "Vol-au-vent", strDrinkAlternate = null, strCategory = "Miscellaneous", strArea = "Belgian",strInstructions = "steek de vol-au-vent in de oven", strMealThumb = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Makaronilaatikko.jpg/1200px-Makaronilaatikko.jpg", strTags = "Ovenschotel", strYoutube = "", dateModified = "", strImageSource = "", strIngredient1 = "aardappelen", strIngredient2 = "gehakt", strIngredient3 = "", strIngredient4 = "", strIngredient5 = "", strIngredient6 = "", strIngredient7 ="", strIngredient8 ="", strIngredient9 ="", strIngredient10 ="", strIngredient11 ="", strIngredient12 ="", strIngredient13 ="", strIngredient14 ="", strIngredient16 ="", strIngredient17 ="", strIngredient18 ="", strIngredient19 ="", strIngredient20 ="", strMeasure1 ="2 patatten", strMeasure2 ="30kg", strMeasure3 ="", strMeasure4 ="" , strMeasure5 ="", strMeasure6 ="",strMeasure7 ="2 patatten", strMeasure8 ="30kg", strMeasure9 ="", strMeasure10 ="" , strMeasure11 ="", strMeasure12 ="" , strMeasure13 ="", strMeasure14 ="",strMeasure15 ="2 patatten", strMeasure16 ="30kg", strMeasure17 ="", strMeasure18 ="" , strMeasure19 ="", strMeasure20 ="", strSource = null, strIngredient15 = "", strCreativeCommonsConfirmed = null)
//                    val meal2 = Meal(idMeal = "2", strMeal = "Flemish Stew", strDrinkAlternate = null, strCategory = "Miscellaneous", strArea = "Belgian",strInstructions = "steek de Flemish Stew in de oven", strMealThumb = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Makaronilaatikko.jpg/1200px-Makaronilaatikko.jpg", strTags = "Ovenschotel", strYoutube = "", dateModified = "", strImageSource = "", strIngredient1 = "aardappelen", strIngredient2 = "gehakt", strIngredient3 = "", strIngredient4 = "", strIngredient5 = "", strIngredient6 = "", strIngredient7 ="", strIngredient8 ="", strIngredient9 ="", strIngredient10 ="", strIngredient11 ="", strIngredient12 ="", strIngredient13 ="", strIngredient14 ="", strIngredient16 ="", strIngredient17 ="", strIngredient18 ="", strIngredient19 ="", strIngredient20 ="", strMeasure1 ="2 patatten", strMeasure2 ="30kg", strMeasure3 ="", strMeasure4 ="" , strMeasure5 ="", strMeasure6 ="",strMeasure7 ="2 patatten", strMeasure8 ="30kg", strMeasure9 ="", strMeasure10 ="" , strMeasure11 ="", strMeasure12 ="" , strMeasure13 ="", strMeasure14 ="",strMeasure15 ="2 patatten", strMeasure16 ="30kg", strMeasure17 ="", strMeasure18 ="" , strMeasure19 ="", strMeasure20 ="", strSource = null, strIngredient15 = "", strCreativeCommonsConfirmed = null)
//                    mealDatabase.mealDao().upsertMeal(meal1)
//                    mealDatabase.mealDao().upsertMeal(meal2)
//                }catch (e: Exception){
//                    println("An error occurred" + e.message)
//                }
//            }
//
//        }

    }

    @Test
    fun insertNewMeal(){
            uiScope.launch {
                withContext(Dispatchers.IO){
                    try {
                        // Maaltijd toevoegen
                        val newMeal = Meal(idMeal = "99999", strMeal = "Ovenschotel", strDrinkAlternate = null, strCategory = "Miscellaneous", strArea = "Belgian",strInstructions = "steek de schotel in de oven", strMealThumb = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Makaronilaatikko.jpg/1200px-Makaronilaatikko.jpg", strTags = "Ovenschotel", strYoutube = "", dateModified = "", strImageSource = "", strIngredient1 = "aardappelen", strIngredient2 = "gehakt", strIngredient3 = "", strIngredient4 = "", strIngredient5 = "", strIngredient6 = "", strIngredient7 ="", strIngredient8 ="", strIngredient9 ="", strIngredient10 ="", strIngredient11 ="", strIngredient12 ="", strIngredient13 ="", strIngredient14 ="", strIngredient16 ="", strIngredient17 ="", strIngredient18 ="", strIngredient19 ="", strIngredient20 ="", strMeasure1 ="2 patatten", strMeasure2 ="30kg", strMeasure3 ="", strMeasure4 ="" , strMeasure5 ="", strMeasure6 ="",strMeasure7 ="2 patatten", strMeasure8 ="30kg", strMeasure9 ="", strMeasure10 ="" , strMeasure11 ="", strMeasure12 ="" , strMeasure13 ="", strMeasure14 ="",strMeasure15 ="2 patatten", strMeasure16 ="30kg", strMeasure17 ="", strMeasure18 ="" , strMeasure19 ="", strMeasure20 ="", strSource = null, strIngredient15 = "", strCreativeCommonsConfirmed = null)
                        mealDatabase.mealDao().upsertMeal(newMeal)

                        // Check of maaltijd is toegevoegd
                        val isInserted = mealDatabase.mealDao().exists("99999")
                        assertEquals(isInserted, true)
                    }catch (e: Exception){
                        println("An error occurred" + e.message)
                    }
                }
            }
    }

    @Test
    @Throws(Exception::class)
    fun deleteMeal(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                try {
                    // Maaltijd toevoegen
                    val newMeal = Meal(idMeal = "99999", strMeal = "Ovenschotel", strDrinkAlternate = null, strCategory = "Miscellaneous", strArea = "Belgian",strInstructions = "steek de schotel in de oven", strMealThumb = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Makaronilaatikko.jpg/1200px-Makaronilaatikko.jpg", strTags = "Ovenschotel", strYoutube = "", dateModified = "", strImageSource = "", strIngredient1 = "aardappelen", strIngredient2 = "gehakt", strIngredient3 = "", strIngredient4 = "", strIngredient5 = "", strIngredient6 = "", strIngredient7 ="", strIngredient8 ="", strIngredient9 ="", strIngredient10 ="", strIngredient11 ="", strIngredient12 ="", strIngredient13 ="", strIngredient14 ="", strIngredient16 ="", strIngredient17 ="", strIngredient18 ="", strIngredient19 ="", strIngredient20 ="", strMeasure1 ="2 patatten", strMeasure2 ="30kg", strMeasure3 ="", strMeasure4 ="" , strMeasure5 ="", strMeasure6 ="",strMeasure7 ="2 patatten", strMeasure8 ="30kg", strMeasure9 ="", strMeasure10 ="" , strMeasure11 ="", strMeasure12 ="" , strMeasure13 ="", strMeasure14 ="",strMeasure15 ="2 patatten", strMeasure16 ="30kg", strMeasure17 ="", strMeasure18 ="" , strMeasure19 ="", strMeasure20 ="", strSource = null, strIngredient15 = "", strCreativeCommonsConfirmed = null)
                    mealDatabase.mealDao().upsertMeal(newMeal)

                    // Check of maaltijd is toegevoegd
                    val isUploaded = mealDatabase.mealDao().exists("99999")

                    // Maaltijd verwijderen
                    mealDatabase.mealDao().delete(newMeal)

                    // Check of maaltijd is verwijderd
                    val isDeleted = !mealDatabase.mealDao().exists("99999")

                    assertEquals(isUploaded, true)
                    assertEquals(isDeleted, true)

                }catch (e: Exception){
                        println("An error occurred" + e.message)
                    }
                }
            }
    }

}