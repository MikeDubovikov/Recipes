package com.mdubovikov.recipes.presentation.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mdubovikov.recipes.databinding.FragmentSavedBinding
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.presentation.adapters.MealAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    val binding: FragmentSavedBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private val viewModel: SavedViewModel by viewModels()
    private val mealAdapter: MealAdapter by lazy { MealAdapter(::onMealClick, ::onSaveIconClicked) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSaved.adapter = mealAdapter
        observeFavoriteMeals()
    }

    private fun observeFavoriteMeals() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedMeals.observe(viewLifecycleOwner) { meals ->
                    meals.let {
                        if (it.isNotEmpty()) {
                            binding.tvNoSaved.visibility = View.GONE
                        } else {
                            binding.tvNoSaved.visibility = View.VISIBLE
                        }
                        mealAdapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun onMealClick(mealId: Int) {
        val action =
            SavedFragmentDirections.actionSavedFragmentToDetailsFragment(mealId = mealId)
        findNavController().navigate(action)
    }

    private fun onSaveIconClicked(meal: MealModel) {
        viewModel.savedIconClicked(meal)
    }

    override fun onDestroyView() {
        binding.rvSaved.adapter = null
        _binding = null
        super.onDestroyView()
    }
}