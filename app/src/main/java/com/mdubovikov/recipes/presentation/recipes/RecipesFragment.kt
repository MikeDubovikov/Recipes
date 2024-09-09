package com.mdubovikov.recipes.presentation.recipes

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
import androidx.navigation.fragment.navArgs
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.databinding.FragmentRecipesBinding
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.presentation.adapters.MealAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding: FragmentRecipesBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private val viewModel: RecipesViewModel by viewModels()
    private val mealAdapter: MealAdapter by lazy { MealAdapter(::onMealClick) }

    private val args by navArgs<RecipesFragmentArgs>()
    private val query by lazy { args.query }
    private val queryKey by lazy { args.queryKey }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMeals.adapter = mealAdapter
        loadData()
    }

    private fun loadData() {
        viewModel.setupQuery(query = query, queryKey = queryKey)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.meals.collectLatest { meals ->
                    with(binding) {
                        when (meals) {
                            is Result.Loading -> {
                                progressBar.visibility = View.VISIBLE
                                rvMeals.visibility = View.GONE
                                tvMealsNotFound.visibility = View.GONE
                                tvMealsError.visibility = View.GONE

                            }

                            is Result.Success -> {
                                if (meals.data.contains(MealModel())) {
                                    tvMealsNotFound.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                } else {
                                    rvMeals.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                    mealAdapter.submitList(meals.data)
                                }
                            }

                            is Result.Error -> {
                                tvMealsError.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onMealClick(mealId: Int) {
        val action =
            RecipesFragmentDirections.actionRecipesFragmentToDetailsFragment(mealId = mealId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        binding.rvMeals.adapter = null
        _binding = null
        super.onDestroyView()
    }
}