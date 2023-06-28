package com.tests.myfinalproject.ui.all_recipes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tests.myfinalproject.data.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRecipesViewModel  @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    val recipes = recipeRepository.getRecipes()
    fun setRecipeIsFavorite(recipeId: Int, isFavorite: Int) {
        viewModelScope.launch {
            recipeRepository.setRecipeIsFavorite(recipeId, isFavorite)
        }
    }
}