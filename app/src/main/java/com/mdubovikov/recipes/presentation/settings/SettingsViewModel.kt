package com.mdubovikov.recipes.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.domain.use_case.GetUserPreferencesUseCase
import com.mdubovikov.recipes.domain.use_case.SetUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val setUserPreferencesUseCase: SetUserPreferencesUseCase
) : ViewModel() {

    suspend fun getTheme() = getUserPreferencesUseCase.getTheme()

    suspend fun getLanguage() = getUserPreferencesUseCase.getLanguage()

    fun setTheme(value: Int) {
        viewModelScope.launch {
            setUserPreferencesUseCase.setTheme(theme = value)
        }
    }

    fun setLanguage(value: Int) {
        viewModelScope.launch {
            setUserPreferencesUseCase.setLanguage(language = value)
        }
    }
}