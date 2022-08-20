package com.example.foodmenu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodmenu.pojo.Meal

@Database(entities = [Meal::class], version = 2)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile //Veranderingen zichtbaar door elke thread
        var INSTANCE: MealDatabase? = null

        @Synchronized //slechts 1 thread kan een instance hebben van deze Room db
        fun getInstance(context: Context): MealDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, MealDatabase::class.java, "meal.db")
                    .fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDatabase
        }
    }
}