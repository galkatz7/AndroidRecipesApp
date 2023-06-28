package com.tests.myfinalproject.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tests.myfinalproject.data.models.Recipe

@Database(entities = [Recipe::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao
    companion object {

        @Volatile
        private var instance : AppDatabase?  = null


        fun getDatabase(context: Context) : AppDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"meals")
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
    }
}