package com.tests.myfinalproject.data.remote_db
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRemoteDataSource @Inject constructor(
    private val recipeService: RecipeService) : BaseDataSource() {

    suspend fun getRecipes() = getResult { recipeService.getAllRecipes() }
    suspend fun getRecipe(id : Int) = getResult { recipeService.getRecipe(id)}
    suspend fun getRandomRecipe() = getResult { recipeService.getRandomRecipe() }

}