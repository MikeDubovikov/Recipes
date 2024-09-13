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
import com.mdubovikov.recipes.common.QueryKeys.CATEGORY
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_AREA
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_FIRST_LETTER
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_INGREDIENT
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_NAME
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.databinding.FragmentHomeBinding
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.presentation.adapters.CategoryAdapter
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
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter(::onClickCategory) }
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
        lifecycleScope.launch { viewModel.selectedItem.collect { selectedItem = it } }
        binding.rvCategories.adapter = categoryAdapter
        binding.buttonCustomSearch.setOnClickListener { selectSearch() }
        setupSearchView()
        loadData()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collectLatest { categories ->
                    with(binding) {
                        when (categories) {
                            is Result.Loading -> {
                                progressBar.visibility = View.VISIBLE
                                rvCategories.visibility = View.GONE
                                tvCategoryError.visibility = View.GONE
                            }

                            is Result.Success -> {
                                rvCategories.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                categoryAdapter.submitList(categories.data)
                            }

                            is Result.Error -> {
                                tvCategoryError.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun toRecipesFragmentWithArgs(query: String, queryKey: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecipesFragment(
            query = query,
            queryKey = queryKey
        )
        findNavController().navigate(action)
    }

    private fun onClickCategory(category: CategoryModel) {
        toRecipesFragmentWithArgs(
            query = category.name,
            queryKey = CATEGORY
        )
    }

    private fun setupSearchView() {
        binding.svMeals.inputType = TYPE_TEXT_FLAG_CAP_SENTENCES
        binding.svMeals.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    when (selectedItem) {
                        0 -> {
                            toRecipesFragmentWithArgs(
                                query = query,
                                queryKey = SEARCH_BY_NAME
                            )
                        }

                        1 -> {
                            toRecipesFragmentWithArgs(
                                query = query,
                                queryKey = SEARCH_BY_FIRST_LETTER
                            )
                        }

                        2 -> {
                            toRecipesFragmentWithArgs(
                                query = query,
                                queryKey = SEARCH_BY_AREA
                            )
                        }

                        3 -> {
                            toRecipesFragmentWithArgs(
                                query = query,
                                queryKey = SEARCH_BY_INGREDIENT
                            )
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

    override fun onDestroyView() {
        binding.rvCategories.adapter = null
        _binding = null
        super.onDestroyView()
    }
}