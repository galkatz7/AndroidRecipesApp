package com.tests.myfinalproject.ui.all_recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tests.myfinalproject.R
import com.tests.myfinalproject.databinding.RecipesFragmentBinding
import com.tests.myfinalproject.utils.Loading
import com.tests.myfinalproject.utils.Success
import com.tests.myfinalproject.utils.Error
import com.tests.myfinalproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllRecipesFragment : Fragment(), RecipesAdapter.RecipeItemListener {

    private val viewModel : AllRecipesViewModel by viewModels()

    private var binding : RecipesFragmentBinding by autoCleared()

    private  lateinit var  adapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipesAdapter(this)
        binding.recipesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.recipesRv.adapter = adapter
        binding.addButton.setOnClickListener {
            onAddRecipeClick()
        }

        viewModel.recipes.observe(viewLifecycleOwner) {
            when(it.status) {
                is Loading -> binding.progressBar.visibility = View.VISIBLE

                is Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.setRecipes(it.status.data!!)
                }

                is Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),it.status.message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onRecipeClick(recipeId: Int) {
        findNavController().navigate(
            R.id.action_allRecipesFragment_to_singleRecipeFragment,
            bundleOf("id" to recipeId))
    }

    override fun onFavoriteClick(recipeId: Int, isFavorite: Int) {
        viewModel.setRecipeIsFavorite(recipeId, isFavorite)
        val updatedRecipe = viewModel.recipes.value?.status?.data?.find { it.idMeal == recipeId }
        updatedRecipe?.let {
            it.isFavoriteMeal = isFavorite
            adapter.updateRecipe(it)
            val message = if (isFavorite == 1) {
                requireContext().getString(R.string.added_msg)
            } else {
                requireContext().getString(R.string.removeded_msg)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetailsClick(recipeId: Int) {
        findNavController().navigate(
            R.id.action_allRecipesFragment_to_singleRecipeFragment,
            bundleOf("id" to recipeId))
    }

    override fun onAddRecipeClick() {
        findNavController().navigate(
            R.id.action_allRecipesFragment_to_addRecipeFragment)
    }
}