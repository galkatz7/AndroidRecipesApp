package com.tests.myfinalproject.ui.all_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tests.myfinalproject.data.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFavoritesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    val favoriteRecipes = repository.getAllFavoriteRecipes()

    fun setRecipeIsFavorite(recipeId: Int, isFavorite: Int) {
        viewModelScope.launch {
            repository.setRecipeIsFavorite(recipeId, isFavorite)
        }
    }
}