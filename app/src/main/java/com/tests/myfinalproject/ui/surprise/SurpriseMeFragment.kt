package com.tests.myfinalproject.ui.surprise

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tests.myfinalproject.data.models.Recipe
import com.tests.myfinalproject.databinding.SurpriseFragmentBinding
import com.tests.myfinalproject.utils.Loading
import com.tests.myfinalproject.utils.Success
import com.tests.myfinalproject.utils.Error
import com.tests.myfinalproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurpriseMeFragment : Fragment() {

    private val viewModel : SurpriseMeViewModal by viewModels()

    private var binding : SurpriseFragmentBinding by autoCleared()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SurpriseFragmentBinding.inflate(inflater,container,false)
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
                    it.status.data?.let { recipe -> updateRecipe(recipe) }
                    binding.recipeCl.visibility = View.VISIBLE
                }
                is Error -> {
                    Log.i("recipes changed", "Error")
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),it.status.message,Toast.LENGTH_LONG).show()
                }
            }
        }

        arguments?.getInt("id")?.let {
            viewModel.setId(it)
        }
    }

    private fun updateRecipe(recipe: Recipe) {
        binding.name.text = recipe.strMeal
        binding.instructions.text = recipe.strInstructions
        Glide.with(requireContext())
            .load(recipe.strMealThumb)
            .circleCrop()
            .into(binding.image)
    }
}
