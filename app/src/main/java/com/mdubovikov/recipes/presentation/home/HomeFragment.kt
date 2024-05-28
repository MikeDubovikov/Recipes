package com.mdubovikov.recipes.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.mdubovikov.recipes.common.Constants.SELECTED_CATEGORY
import com.mdubovikov.recipes.common.Constants.SELECTED_SEARCH
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
    private lateinit var binding: FragmentHomeBinding
    private var selectedItem by Delegates.notNull<Int>()
    private var selectedCategory by Delegates.notNull<String>()
    private val viewModel: HomeViewModel by viewModels()
    private val mealAdapter: MealAdapter by lazy { MealAdapter(::onMealClick, ::onSaveIconClicked) }
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter(::onClickCategory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedItem = savedInstanceState?.getInt(SELECTED_SEARCH) ?: 0
        selectedCategory =
            savedInstanceState?.getString(SELECTED_CATEGORY) ?: resources.getString(R.string.meals)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        setupSearchView()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_SEARCH, selectedItem)
        outState.putString(SELECTED_CATEGORY, selectedCategory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvMeals.adapter = mealAdapter
            rvCategories.adapter = categoryAdapter
            tvCategoryMeal.text = selectedCategory
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collectLatest { categories ->
                    when (categories) {
                        is Result.Loading -> {}

                        is Result.Success -> {
                            categoryAdapter.submitList(categories.data)
                        }

                        is Result.Error -> {}
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.meals.collectLatest { meals ->
                    when (meals) {
                        is Result.Loading -> {}

                        is Result.Success -> {
                            if (meals.data?.contains(MealModel()) == true) {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.meals_not_found),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                mealAdapter.submitList(meals.data)
                            }
                        }

                        is Result.Error -> {}
                    }
                }
            }
        }

        binding.buttonCustomSearch.setOnClickListener {
            selectSearch()
        }
    }

    private fun changeCategoryName(categoryName: String) = binding.apply {
        selectedCategory = categoryName
        tvCategoryMeal.text = selectedCategory
    }

    private fun onClickCategory(category: CategoryModel) = binding.apply {
        viewModel.setupQuery(category.name, CATEGORY)
        changeCategoryName(category.name)
    }

    private fun setupSearchView() = binding.svMeals.setOnQueryTextListener(object :
        SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                when (selectedItem) {
                    0 -> {
                        viewModel.setupQuery(query, SEARCH_BY_NAME)
                        changeCategoryName("${getString(R.string.search)}: $query")
                    }

                    1 -> {
                        viewModel.setupQuery(query, SEARCH_BY_FIRST_LETTER)
                        changeCategoryName("${getString(R.string.search)}: $query")
                    }

                    2 -> {
                        viewModel.setupQuery(query, SEARCH_BY_AREA)
                        changeCategoryName("${getString(R.string.search)}: $query")
                    }

                    3 -> {
                        viewModel.setupQuery(query, SEARCH_BY_INGREDIENT)
                        changeCategoryName("${getString(R.string.search)}: $query")
                    }
                }
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })

    private fun selectSearch() {
        val searchItems = resources.getStringArray(R.array.searches)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.choose_search)
            .setSingleChoiceItems(searchItems, selectedItem) { dialog, which ->
                selectedItem = which
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
        super.onDestroyView()
        binding.rvCategories.adapter = null
        binding.rvMeals.adapter = null
    }
}