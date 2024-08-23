package com.mdubovikov.recipes.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.data.mapper.MealMapper
import com.mdubovikov.recipes.domain.model.MealDetailsModel
import com.mdubovikov.recipes.domain.use_case.ChangeSavedStatusMealUseCase
import com.mdubovikov.recipes.domain.use_case.GetMealDetailsUseCase
import com.mdubovikov.recipes.domain.use_case.IsMealInSavedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
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

    private val _imageButton = MutableLiveData<Int>()
    val imageButton: LiveData<Int> = _imageButton

    private val _saveOrSavedText = MutableLiveData<Int>()
    val saveOrSavedText: LiveData<Int> = _saveOrSavedText

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
            _imageButton.postValue(R.drawable.ic_heart_filled)
            _saveOrSavedText.postValue(R.string.saved)
        } else {
            _imageButton.postValue(R.drawable.ic_heart_linear)
            _saveOrSavedText.postValue(R.string.save)
        }
    }
}