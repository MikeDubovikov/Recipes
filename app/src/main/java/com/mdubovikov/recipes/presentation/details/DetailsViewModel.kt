package com.mdubovikov.recipes.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.data.mapper.MealMapper
import com.mdubovikov.recipes.di.IoDispatcher
import com.mdubovikov.recipes.domain.model.MealDetailsModel
import com.mdubovikov.recipes.domain.use_case.ChangeSavedStatusMealUseCase
import com.mdubovikov.recipes.domain.use_case.GetMealDetailsUseCase
import com.mdubovikov.recipes.domain.use_case.IsMealInSavedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMealDetailsUseCase: GetMealDetailsUseCase,
    private val changeSavedStatusMealUseCase: ChangeSavedStatusMealUseCase,
    private val isMealInSavedUseCase: IsMealInSavedUseCase,
    private val mapper: MealMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mealId: MutableStateFlow<Int?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val meal: StateFlow<Result<MealDetailsModel>> = mealId
        .flatMapLatest { mealId ->
            if (mealId != null) {
                getMealDetailsUseCase.invoke(mealId = mealId)
            } else flowOf()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading
        )

    private val _imageButton: MutableStateFlow<Int> = MutableStateFlow(R.drawable.ic_heart_linear)
    val imageButton: StateFlow<Int> = _imageButton.asStateFlow()

    private val _saveOrSavedText: MutableStateFlow<Int> = MutableStateFlow(R.string.save)
    val saveOrSavedText: StateFlow<Int> = _saveOrSavedText.asStateFlow()

    fun saveMeal(mealDetailsModel: MealDetailsModel) {
        viewModelScope.launch(dispatcher) {
            changeSavedStatusMealUseCase.changeStatus(
                mapper.mapMealDetailsModelToMealModel(
                    mealDetailsModel
                )
            )
            setIconInButton(mealDetailsModel.id)
        }
    }

    suspend fun setupMealId(mealId: Int) {
        this.mealId.value = mealId
        setIconInButton(mealId.toString())
    }

    private suspend fun setIconInButton(mealId: String) {
        if (isMealInSavedUseCase.isMealInSaved(mealId)) {
            _imageButton.value = R.drawable.ic_heart_filled
            _saveOrSavedText.value = R.string.saved
        } else {
            _imageButton.value = R.drawable.ic_heart_linear
            _saveOrSavedText.value = R.string.save
        }
    }
}