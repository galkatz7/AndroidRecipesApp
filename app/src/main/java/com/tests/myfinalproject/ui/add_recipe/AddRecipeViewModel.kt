package com.tests.myfinalproject.ui.add_recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tests.myfinalproject.data.models.Recipe
import com.tests.myfinalproject.data.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private fun generateUniqueId(): Int {
        while (true) {
            val uniqueID = UUID.randomUUID().toString().hashCode()
            val recipe = recipeRepository.getRecipe(uniqueID).value
            if (recipe?.status?.data == null) {
                return uniqueID
            }
        }
    }

    fun addRecipe(name: String, category: String, instructions: String) {
        viewModelScope.launch {
            val newRecipeId = generateUniqueId()
            val newRecipe = Recipe(
                idMeal = newRecipeId,
                strMeal = name,
                strCategory = category,
                strInstructions = instructions,
                isLocal = 1
            )
            recipeRepository.insertRecipe(newRecipe)
        }
    }
}
