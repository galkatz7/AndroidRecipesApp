package com.tests.myfinalproject.ui.single_recipe

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.tests.myfinalproject.R
import com.tests.myfinalproject.data.models.Recipe
import com.tests.myfinalproject.databinding.RecipeDetailFragmentBinding
import com.tests.myfinalproject.utils.Loading
import com.tests.myfinalproject.utils.Success
import com.tests.myfinalproject.utils.Error
import com.tests.myfinalproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleRecipeFragment : Fragment() {

    private val viewModel: SingleRecipeViewModel by viewModels()

    private var binding: RecipeDetailFragmentBinding by autoCleared()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.recipe.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    Log.i("recipes changed", "Loading")
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recipeCl.visibility = View.GONE
                }

                is Success -> {
                    Log.i("recipes changed", "Success")
                    binding.progressBar.visibility = View.GONE
                    it.status.data?.let { recipe ->
                        updateRecipe(recipe)
                        val isLocal = recipe.isLocal
                        if (isLocal == 0) {
                            binding.deleteButton.visibility = View.GONE
                        } else {
                            binding.deleteButton.visibility = View.VISIBLE
                        }
                    }
                    binding.recipeCl.visibility = View.VISIBLE
                }

                is Error -> {
                    Log.i("recipes changed", "Error")
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        arguments?.getInt("id")?.let {
            viewModel.setId(it)
        }

        arguments?.getInt("id")?.let {
            val id = it
            binding.deleteButton.setOnClickListener {
                val alertDialog = android.app.AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.delete_recipe))
                    .setMessage(getString(R.string.delete_msg))
                    .setPositiveButton(getString(R.string.delete)) { dialog: DialogInterface, _: Int ->
                        viewModel.onDeleteClick(id)
                        findNavController().navigateUp()

                        Toast.makeText(context, R.string.deleted_msg, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()

                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int ->
                        Toast.makeText(context, R.string.canceled_msg, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .create()

                alertDialog.show()
            }
        }
    }

    private fun updateRecipe(recipe: Recipe) {
        binding.name.text = recipe.strMeal
        binding.instructions.text = recipe.strInstructions
        val imagePath = recipe.strMealThumb ?: R.drawable.ic_recipes_launcher
        Glide.with(requireContext())
            .load(imagePath)
            .circleCrop()
            .into(binding.image)
    }
}

