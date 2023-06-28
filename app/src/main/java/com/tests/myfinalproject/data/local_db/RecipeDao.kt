package com.tests.myfinalproject.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tests.myfinalproject.data.models.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipesTable")
    fun getAllRecipes() : LiveData<List<Recipe>>

    @Query("SELECT * FROM recipesTable WHERE idMeal = :id")
    fun getRecipe(id : Int) : LiveData<Recipe>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipes(recipes : List<Recipe>)

    @Query("DELETE FROM recipesTable WHERE idMeal = :id")
    suspend fun deleteRecipe(id: Int)

    @Query("SELECT * FROM recipesTable WHERE isFavoriteMeal = 1")
    fun getAllFavoriteRecipes() : LiveData<List<Recipe>>

    @Query("UPDATE recipesTable SET isFavoriteMeal = :isFavorite WHERE idMeal = :id")
    suspend fun updateRecipeIsFavorite(id: Int, isFavorite: Int)

    @Query("SELECT * FROM recipesTable ORDER BY RANDOM() LIMIT 1")
    fun getRandomRecipe(): LiveData<Recipe>
}

