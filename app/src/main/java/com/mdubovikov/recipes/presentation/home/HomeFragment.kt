package com.mdubovikov.recipes.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.common.Constants.Queries.CATEGORY
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_AREA
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_FIRST_LETTER
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_INGREDIENT
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_NAME
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.databinding.FragmentHomeBinding
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.presentation.adapters.CategoryAdapter
import com.mdubovikov.recipes.presentation.adapters.MealAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private val viewModel: HomeViewModel by viewModels()
    private val mealAdapter: MealAdapter by lazy { MealAdapter(::onMealClick, ::onSaveIconClicked) }
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter(::onClickCategory) }

    private var selectedCategory by Delegates.notNull<String>()
    private var selectedItem by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.query.collect {
                selectedCategory = it
            }
        }
        lifecycleScope.launch {
            viewModel.selectedItem.collect {
                selectedItem = it
            }
        }
        setupSearchView()
        with(binding) {
            rvMeals.adapter = mealAdapter
            rvCategories.adapter = categoryAdapter
            tvCategoryMeal.text = selectedCategory
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collectLatest { categories ->
                    with(binding) {
                        when (categories) {
                            is Result.Loading -> {
                                cvErrorCategory.visibility = View.GONE
                                rvCategories.visibility = View.VISIBLE
                            }

                            is Result.Success -> {
                                cvErrorCategory.visibility = View.GONE
                                rvCategories.visibility = View.VISIBLE

                                categoryAdapter.submitList(categories.data)
                            }

                            is Result.Error -> {
                                cvErrorCategory.visibility = View.VISIBLE
                                rvCategories.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.meals.collectLatest { meals ->
                    with(binding) {
                        when (meals) {
                            is Result.Loading -> {
                                pbMeals.visibility = View.VISIBLE
                            }

                            is Result.Success -> {
                                if (meals.data.contains(MealModel())) {
                                    pbMeals.visibility = View.GONE
                                    rvMeals.visibility = View.GONE
                                    cvErrorMeals.visibility = View.GONE
                                    cvMealsNotFound?.visibility = View.VISIBLE
                                } else {
                                    pbMeals.visibility = View.GONE
                                    cvErrorMeals.visibility = View.GONE
                                    cvMealsNotFound?.visibility = View.GONE
                                    rvMeals.visibility = View.VISIBLE
                                    mealAdapter.submitList(meals.data)
                                }
                            }

                            is Result.Error -> {
                                pbMeals.visibility = View.GONE
                                rvMeals.visibility = View.GONE
                                cvMealsNotFound?.visibility = View.GONE
                                cvErrorMeals.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }

        binding.buttonCustomSearch.setOnClickListener {
            selectSearch()
        }
    }

    private fun onClickCategory(category: CategoryModel) = binding.apply {
        viewModel.setupQuery(category.name, CATEGORY)
        tvCategoryMeal.text = category.name
    }

    private fun setupSearchView() {
        binding.svMeals.inputType = TYPE_TEXT_FLAG_CAP_SENTENCES
        binding.svMeals.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    when (selectedItem) {
                        0 -> {
                            viewModel.setupQuery(query, SEARCH_BY_NAME)
                            binding.tvCategoryMeal.text = selectedCategory
                        }

                        1 -> {
                            viewModel.setupQuery(query, SEARCH_BY_FIRST_LETTER)
                            binding.tvCategoryMeal.text = selectedCategory
                        }

                        2 -> {
                            viewModel.setupQuery(query, SEARCH_BY_AREA)
                            binding.tvCategoryMeal.text = selectedCategory
                        }

                        3 -> {
                            viewModel.setupQuery(query, SEARCH_BY_INGREDIENT)
                            binding.tvCategoryMeal.text = selectedCategory
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun selectSearch() {
        val searchItems = resources.getStringArray(R.array.searches)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.choose_search)
            .setSingleChoiceItems(searchItems, selectedItem) { dialog, which ->
                viewModel.changeSelectedItem(value = which)
                binding.svMeals.setQuery("", false)
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun onMealClick(mealId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(mealId = mealId)
        findNavController().navigate(action)
    }

    private fun onSaveIconClicked(meal: MealModel) {
        viewModel.savedIconClicked(meal)
    }

    override fun onDestroyView() {
        binding.rvCategories.adapter = null
        binding.rvMeals.adapter = null
        _binding = null
        super.onDestroyView()
    }
}