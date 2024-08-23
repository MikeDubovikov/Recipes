package com.mdubovikov.recipes.presentation.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.use_case.ChangeSavedStatusMealUseCase
import com.mdubovikov.recipes.domain.use_case.GetSavedMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    getSavedMealsUseCase: GetSavedMealsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val changeSavedStatusMealUseCase: ChangeSavedStatusMealUseCase
) : ViewModel() {

    val savedMeals: LiveData<List<MealModel>> = getSavedMealsUseCase.invoke().asLiveData()

    fun savedIconClicked(meal: MealModel) {
        viewModelScope.launch(dispatcher) {
            changeSavedStatusMealUseCase.changeStatus(meal)
        }
    }
}