package com.tests.myfinalproject.ui.all_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tests.myfinalproject.R
import com.tests.myfinalproject.data.models.Recipe
import com.tests.myfinalproject.databinding.ItemRecipeBinding

class RecipesAdapter(private val listener : RecipeItemListener) :
    RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    private val recipes = ArrayList<Recipe>()

    class RecipeViewHolder(private val itemBinding: ItemRecipeBinding,
                           private val listener: RecipeItemListener)
        : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var recipe: Recipe

        init {
            itemBinding.root.setOnClickListener(this)
            itemBinding.favoriteButton.setOnClickListener {
                recipe.isFavoriteMeal = 1 - recipe.isFavoriteMeal
                bindFavoriteButton()
                listener.onFavoriteClick(recipe.idMeal, recipe.isFavoriteMeal)
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
            bindFavoriteButton()
        }

        private fun bindFavoriteButton() {
            if (recipe.isFavoriteMeal == 1) {
                itemBinding.favoriteButton.setImageResource(R.drawable.filled_star)
            } else {
                itemBinding.favoriteButton.setImageResource(R.drawable.empty_star)
            }
        }

        override fun onClick(v: View?) {
            listener.onRecipeClick(recipe.idMeal)
        }
    }

    fun setRecipes(recipes : Collection<Recipe>) {
        this.recipes.clear()
        val validRecipes = recipes.filter { it.idMeal != null && it.strMeal != null }
        this.recipes.addAll(validRecipes)
        notifyDataSetChanged()
    }

    fun updateRecipe(updatedRecipe: Recipe) {
        val index = recipes.indexOfFirst { it.idMeal == updatedRecipe.idMeal }
        if (index != -1) {
            recipes[index] = updatedRecipe
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecipeViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
        holder.bind(recipes[position])


    override fun getItemCount() = recipes.size

    interface RecipeItemListener {
        fun onRecipeClick(recipeId : Int)
        fun onFavoriteClick(recipeId : Int, idFavorite : Int)
        fun onDetailsClick(recipeId : Int)
        fun onAddRecipeClick()

    }

}