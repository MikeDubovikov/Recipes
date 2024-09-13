package com.mdubovikov.recipes.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.recipes.Language
import com.mdubovikov.recipes.Theme
import com.mdubovikov.recipes.domain.use_case.GetUserPreferencesUseCase
import com.mdubovikov.recipes.domain.use_case.SetUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val setUserPreferencesUseCase: SetUserPreferencesUseCase
) : ViewModel() {

    private var _theme = MutableStateFlow(Theme.BY_DEFAULT_THEME)
    val theme = _theme.asStateFlow()

    private var _language = MutableStateFlow(Language.BY_DEFAULT_LANGUAGE)
    val language = _language.asStateFlow()

    init {
        getTheme()
        getLanguage()
    }

    private fun getTheme() {
        viewModelScope.launch {
            getUserPreferencesUseCase.settings.collect {
                _theme.value = it.theme
            }
        }
    }

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            setUserPreferencesUseCase.setTheme(theme)
        }
    }

    private fun getLanguage() {
        viewModelScope.launch {
            getUserPreferencesUseCase.settings.collect {
                _language.value = it.language
            }
        }
    }

    fun setLanguage(language: Language) {
        viewModelScope.launch {
            setUserPreferencesUseCase.setLanguage(language)
        }
    }
}