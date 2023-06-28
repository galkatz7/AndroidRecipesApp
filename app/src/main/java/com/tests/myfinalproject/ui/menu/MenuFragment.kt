package com.tests.myfinalproject.ui.menu

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.tests.myfinalproject.R

class MenuFragment : Fragment(), View.OnClickListener {
    private lateinit var Title: TextView
    private lateinit var recipesButton: Button
    private lateinit var favoritesButton: Button
    private lateinit var surpriseButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.menu_fragment, container, false)
        Title = view.findViewById(R.id.title_textview)
        recipesButton = view.findViewById(R.id.all_recipes_button)
        favoritesButton = view.findViewById(R.id.all_favorites_button)
        surpriseButton = view.findViewById(R.id.surprise_button)
        recipesButton.setOnClickListener(this)
        favoritesButton.setOnClickListener(this)
        surpriseButton.setOnClickListener(this)
        Title.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                Title.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        Title.startAnimation(animation)

        val animation1 = AlphaAnimation(0f, 1f)
        animation1.duration = 1000
        animation1.interpolator = DecelerateInterpolator()
        val animation2 = ScaleAnimation(
            0.5f, 1.0f, 0.5f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        animation2.duration = 1000
        animation2.interpolator = DecelerateInterpolator()
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(animation1)
        animationSet.addAnimation(animation2)

        recipesButton.startAnimation(animationSet)
        favoritesButton.startAnimation(animationSet)
        surpriseButton.startAnimation(animationSet)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.all_recipes_button -> {
                findNavController().navigate(R.id.action_menuFragment_to_allRecipesFragment)
            }
            R.id.all_favorites_button -> {
                findNavController().navigate(R.id.action_menuFragment_to_allFavoritesFragment)
            }
            R.id.surprise_button -> {
                findNavController().navigate(R.id.action_menuFragment_to_surpriseMeFragment)
            }
        }
    }
}
