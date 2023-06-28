package com.tests.myfinalproject.ui.single_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.tests.myfinalproject.data.models.Recipe
import com.tests.myfinalproject.data.repositories.RecipeRepository
import com.tests.myfinalproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleRecipeViewModel @Inject constructor(
        private val recipeRepository: RecipeRepository
    ): ViewModel() {

        private val _id =  MutableLiveData<Int>()

        private val _recipe = _id.switchMap {
            recipeRepository.getRecipe(it)
        }

        val recipe : LiveData<Resource<Recipe>> = _recipe

        fun setId(id : Int) {
            _id.value = id
        }

    fun onDeleteClick(id: Int) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(id)
        }
    }
}