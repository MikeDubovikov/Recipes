package com.mdubovikov.recipes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.domain.use_case.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _selectedItem: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedItem: StateFlow<Int> = _selectedItem.asStateFlow()

    val categories: StateFlow<Result<List<CategoryModel>>> = getCategoriesUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Result.Loading
        )

    fun changeSelectedItem(value: Int) {
        _selectedItem.value = value
    }
}