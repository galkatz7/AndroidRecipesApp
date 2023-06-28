package com.tests.myfinalproject.ui.add_recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tests.myfinalproject.R
import com.tests.myfinalproject.databinding.AddRecipeFragmentBinding
import com.tests.myfinalproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRecipeFragment : Fragment(), View.OnClickListener {
    private val viewModel: AddRecipeViewModel by viewModels()
    private var binding: AddRecipeFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddRecipeFragmentBinding.inflate(inflater, container, false)
        binding.addButton.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addButton -> {
                val name = binding.recipeNameEditText.text.toString()
                val category = binding.ingredientsEditText.text.toString()
                val instructions = binding.instructionsEditText.text.toString()

                if (name.isEmpty() || category.isEmpty() || instructions.isEmpty()) {
                    Toast.makeText(context, R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addRecipe(name, category, instructions)
                    findNavController().navigateUp()
                    Toast.makeText(context, R.string.added_msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
