package com.mdubovikov.recipes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.common.Constants.Queries.CATEGORY
import com.mdubovikov.recipes.common.Constants.Queries.RANDOM
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_AREA
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_FIRST_LETTER
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_INGREDIENT
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_NAME
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.use_case.AddOrRemoveMealUseCase
import com.mdubovikov.recipes.domain.use_case.GetCategoriesUseCase
import com.mdubovikov.recipes.domain.use_case.GetMealsUseCase
import com.mdubovikov.recipes.domain.use_case.GetRandomMealsUseCase
import com.mdubovikov.recipes.domain.use_case.IsMealInSavedUseCase
import com.mdubovikov.recipes.domain.use_case.SearchMealsByAreaUseCase
import com.mdubovikov.recipes.domain.use_case.SearchMealsByFirstLetterUseCase
import com.mdubovikov.recipes.domain.use_case.SearchMealsByIngredientUseCase
import com.mdubovikov.recipes.domain.use_case.SearchMealsByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase,
    private val getRandomMealsUseCase: GetRandomMealsUseCase,
    private val searchMealsByNameUseCase: SearchMealsByNameUseCase,
    private val searchMealsByAreaUseCase: SearchMealsByAreaUseCase,
    private val searchMealsByFirstLetterUseCase: SearchMealsByFirstLetterUseCase,
    private val searchMealsByIngredientUseCase: SearchMealsByIngredientUseCase,
    private val isMealInSavedUseCase: IsMealInSavedUseCase,
    private val addOrRemoveMealUseCase: AddOrRemoveMealUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val query: MutableStateFlow<String?> = MutableStateFlow(null)

    private lateinit var queryKey: String

    init {
        setupQuery()
    }

    val categories: StateFlow<Result<List<CategoryModel>>> = getCategoriesUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val meals: StateFlow<Result<List<MealModel>>> = query
        .flatMapLatest { query ->
            if (query != null) {
                when (queryKey) {
                    CATEGORY -> getMealsUseCase.invoke(category = query)
                    SEARCH_BY_NAME -> searchMealsByNameUseCase.invoke(query = query)
                    SEARCH_BY_AREA -> searchMealsByAreaUseCase.invoke(query = query)
                    SEARCH_BY_FIRST_LETTER -> searchMealsByFirstLetterUseCase.invoke(query = query)
                    SEARCH_BY_INGREDIENT -> searchMealsByIngredientUseCase.invoke(query = query)
                    else -> getRandomMealsUseCase.invoke()
                }
            } else flowOf()
        }
        .map(::processResult)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading
        )

    fun setupQuery(query: String = RANDOM, queryKey: String = RANDOM) {
        this.queryKey = queryKey
        this.query.value = query
    }

    private suspend fun processResult(result: Result<List<MealModel>>): Result<List<MealModel>> =
        when (result) {
            is Result.Loading -> Result.Loading

            is Result.Success -> {
                if (result.data != null) {
                    val data = result.data
                        .map { item ->
                            if (isMealInSavedUseCase.isMealInSaved(item.id))
                                item.copy(isSaved = item.isSaved.not())
                            else
                                item
                        }
                    Result.Success(data)
                } else
                    TODO()
            }

            is Result.Error -> Result.Error(result.error)
        }

    fun savedIconClicked(meal: MealModel) {
        viewModelScope.launch(dispatcher) {
            addOrRemoveMealUseCase.addOrRemoveMeal(meal)
        }
    }
}