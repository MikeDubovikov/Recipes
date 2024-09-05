package com.mdubovikov.recipes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.common.Constants.Queries.CATEGORY
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_AREA
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_FIRST_LETTER
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_INGREDIENT
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_NAME
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.di.IoDispatcher
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.use_case.ChangeSavedStatusMealUseCase
import com.mdubovikov.recipes.domain.use_case.GetCategoriesUseCase
import com.mdubovikov.recipes.domain.use_case.GetMealsUseCase
import com.mdubovikov.recipes.domain.use_case.IsMealInSavedUseCase
import com.mdubovikov.recipes.domain.use_case.SearchMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase,
    private val searchMealsUseCase: SearchMealsUseCase,
    private val isMealInSavedUseCase: IsMealInSavedUseCase,
    private val changeSavedStatusMealUseCase: ChangeSavedStatusMealUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _query: MutableStateFlow<String> = MutableStateFlow("Beef")
    val query: StateFlow<String> = _query.asStateFlow()

    private val queryKey: MutableStateFlow<String> = MutableStateFlow(CATEGORY)

    private val _selectedItem: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedItem: StateFlow<Int> = _selectedItem.asStateFlow()

    val categories: StateFlow<Result<List<CategoryModel>>> = getCategoriesUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Result.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val meals: StateFlow<Result<List<MealModel>>> = _query
        .flatMapLatest { query ->
            when (queryKey.value) {
                CATEGORY -> getMealsUseCase.invoke(category = query)

                SEARCH_BY_NAME -> searchMealsUseCase.search(
                    query = query,
                    searchKey = SEARCH_BY_NAME
                )

                SEARCH_BY_FIRST_LETTER -> searchMealsUseCase.search(
                    query = query,
                    searchKey = SEARCH_BY_FIRST_LETTER
                )

                SEARCH_BY_AREA -> searchMealsUseCase.search(
                    query = query,
                    searchKey = SEARCH_BY_AREA
                )

                SEARCH_BY_INGREDIENT -> searchMealsUseCase.search(
                    query = query,
                    searchKey = SEARCH_BY_INGREDIENT
                )

                else -> throw IllegalStateException("Unknown query state")
            }
        }
        .map(::processResult)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Result.Loading
        )

    private suspend fun processResult(result: Result<List<MealModel>>): Result<List<MealModel>> =
        when (result) {
            is Result.Loading -> Result.Loading

            is Result.Success -> {
                val data = result.data
                    .map { item ->
                        if (isMealInSavedUseCase.isMealInSaved(item.id))
                            item.copy(isSaved = item.isSaved.not())
                        else
                            item
                    }
                Result.Success(data)
            }

            is Result.Error -> Result.Error(result.error)
        }

    fun savedIconClicked(meal: MealModel) {
        viewModelScope.launch(dispatcher) {
            changeSavedStatusMealUseCase.changeStatus(meal)
        }
    }

    fun setupQuery(query: String, queryKeyValue: String) {
        queryKey.value = queryKeyValue
        _query.value = query
    }

    fun changeSelectedItem(value: Int) {
        _selectedItem.value = value
    }
}