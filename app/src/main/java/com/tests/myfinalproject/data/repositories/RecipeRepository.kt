package com.tests.myfinalproject.data.repositories

import com.tests.myfinalproject.data.local_db.RecipeDao
import com.tests.myfinalproject.data.models.Recipe
import com.tests.myfinalproject.data.remote_db.RecipeRemoteDataSource
import com.tests.myfinalproject.utils.performFetchingAndSaving
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val remoteDataSource : RecipeRemoteDataSource,
    private val localDataSource : RecipeDao
){

    fun getRecipes() = performFetchingAndSaving(
        {localDataSource.getAllRecipes()},
        {remoteDataSource.getRecipes()},
        {localDataSource.insertRecipes(it.meals)}
    )

    fun getRecipe(id : Int) = performFetchingAndSaving(
        {localDataSource.getRecipe(id)},
        {remoteDataSource.getRecipe(id)},
        {localDataSource.insertRecipe(it)}
    )
    fun getAllFavoriteRecipes() = localDataSource.getAllFavoriteRecipes()

    suspend fun setRecipeIsFavorite(id : Int, isFavorite: Int) = withContext(Dispatchers.IO) {
        localDataSource.updateRecipeIsFavorite(id, isFavorite)
    }

    fun getRandomRecipe() = performFetchingAndSaving(
        { localDataSource.getRandomRecipe() },
        { remoteDataSource.getRandomRecipe() },
        { localDataSource.insertRecipe(it) }
    )

    suspend fun insertRecipe(recipe: Recipe) = withContext(Dispatchers.IO) {
        localDataSource.insertRecipe(recipe)
    }

    suspend fun deleteRecipe(id: Int) = withContext(Dispatchers.IO) {
        localDataSource.deleteRecipe(id)
    }


}