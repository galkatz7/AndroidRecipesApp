package com.tests.myfinalproject.data.remote_db
import com.tests.myfinalproject.data.models.AllRecipes
import com.tests.myfinalproject.data.models.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET("json/v1/1/search.php?f=s")
    suspend fun getAllRecipes() : Response<AllRecipes>

    @GET("json/v1/1/lookup.php?")
    suspend fun getRecipe(@Query("i") id : Int) : Response<Recipe>

    @GET("json/v1/1/random.php")
    suspend fun getRandomRecipe() : Response<Recipe>
}
