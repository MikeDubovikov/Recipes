package com.mdubovikov.recipes.presentation.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.common.QueryKeys.CATEGORY
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_AREA
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_FIRST_LETTER
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_INGREDIENT
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_NAME
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.use_case.GetMealsUseCase
import com.mdubovikov.recipes.domain.use_case.SearchMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getMealsUseCase: GetMealsUseCase,
    private val searchMealsUseCase: SearchMealsUseCase
) : ViewModel() {

    private val query: MutableStateFlow<String> = MutableStateFlow("")
    private val queryKey: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val meals: StateFlow<Result<List<MealModel>>> = query
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
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Result.Loading
        )

    fun setupQuery(query: String, queryKey: String) {
        this.queryKey.value = queryKey
        this.query.value = query
    }
}