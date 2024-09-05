package com.mdubovikov.recipes.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.di.MainDispatcher
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.use_case.ChangeSavedStatusMealUseCase
import com.mdubovikov.recipes.domain.use_case.GetSavedMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    getSavedMealsUseCase: GetSavedMealsUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
    private val changeSavedStatusMealUseCase: ChangeSavedStatusMealUseCase
) : ViewModel() {

    private val _savedMeals: MutableStateFlow<List<MealModel>> = MutableStateFlow(listOf())
    val savedMeals: StateFlow<List<MealModel>> = _savedMeals.asStateFlow()

    init {
        viewModelScope.launch {
            getSavedMealsUseCase.invoke().collect {
                _savedMeals.value = it
            }
        }
    }

    fun savedIconClicked(meal: MealModel) {
        viewModelScope.launch(dispatcher) {
            changeSavedStatusMealUseCase.changeStatus(meal)
        }
    }
}