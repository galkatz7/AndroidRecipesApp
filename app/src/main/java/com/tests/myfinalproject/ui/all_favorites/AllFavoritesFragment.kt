package com.tests.myfinalproject.ui.all_favorites

import android.content.DialogInterface
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
import com.tests.myfinalproject.databinding.FavoritesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFavoritesFragment : Fragment() {

    private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AllFavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        adapter = FavoritesAdapter(object : FavoritesAdapter.RecipeItemListener {
            override fun onRecipeClick(recipeId: Int) {
                findNavController().navigate(
                    R.id.action_allFavoritesFragment_to_singleRecipeFragment,
                    bundleOf("id" to recipeId))
            }
            override fun onDetailsClick(recipeId: Int){
                findNavController().navigate(
                    R.id.action_allFavoritesFragment_to_singleRecipeFragment,
                    bundleOf("id" to recipeId)
                )
            }
            override fun onRemoveClick(recipeId: Int, isFavorite: Int) {
                val alertDialog = android.app.AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.remove_favorite))
                    .setMessage(getString(R.string.remove_msg))
                    .setPositiveButton(getString(R.string.remove)) { dialog: DialogInterface, _: Int ->
                        viewModel.setRecipeIsFavorite(recipeId, isFavorite)
                        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
                            adapter.setFavorites(recipes)
                        }
                        Toast.makeText(context, R.string.removeded_msg, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int ->
                        Toast.makeText(context, R.string.canceled_msg, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .create()

                alertDialog.show()
            }

        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
            adapter.setFavorites(recipes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
