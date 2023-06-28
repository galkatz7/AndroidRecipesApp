package com.tests.myfinalproject.ui.all_favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tests.myfinalproject.R
import com.tests.myfinalproject.data.models.Recipe
import com.tests.myfinalproject.databinding.ItemFavoriteBinding

class FavoritesAdapter(private val listener : RecipeItemListener) :
    RecyclerView.Adapter<FavoritesAdapter.RecipeViewHolder>() {

    private val favorites = ArrayList<Recipe>()

    class RecipeViewHolder(private val itemBinding: ItemFavoriteBinding,
                           private val listener: RecipeItemListener)
        : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var recipe: Recipe

        init {
            itemBinding.root.setOnClickListener(this)
            itemBinding.removeButton.setOnClickListener {
                recipe.isFavoriteMeal = 1 - recipe.isFavoriteMeal
                listener.onRemoveClick(recipe.idMeal, recipe.isFavoriteMeal)
            }
            itemBinding.detailsButton.setOnClickListener {
                listener.onDetailsClick(recipe.idMeal)
            }
        }

        fun bind(item: Recipe) {
            this.recipe = item
            itemBinding.name.text = item.strMeal
            itemBinding.category.text = item.strCategory
            val imagePath = item.strMealThumb ?: R.drawable.ic_recipes_launcher
            Glide.with(itemBinding.root)
                .load(imagePath)
                .circleCrop()
                .into(itemBinding.image)
        }

        override fun onClick(v: View?) {
            listener.onRecipeClick(recipe.idMeal)
        }
    }

    fun setFavorites(favorites : Collection<Recipe>) {
        this.favorites.clear()
        this.favorites.addAll(favorites)
        notifyDataSetChanged()
    }

    fun updateRecipe(updatedRecipe: Recipe) {
        val index = favorites.indexOfFirst { it.idMeal == updatedRecipe.idMeal }
        if (index != -1) {
            favorites[index] = updatedRecipe
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecipeViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
        holder.bind(favorites[position])

    override fun getItemCount() = favorites.size

    interface RecipeItemListener {
        fun onRecipeClick(recipeId : Int)
        fun onDetailsClick(recipeId : Int)
        fun onRemoveClick(recipeId : Int, idFavorite : Int)
    }
}