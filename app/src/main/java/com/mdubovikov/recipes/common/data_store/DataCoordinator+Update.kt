package com.mdubovikov.recipes.common.data_store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun DataCoordinator.updateTheme(value: Int) {
    this.theme = value
    CoroutineScope(Dispatchers.Default).launch {
        setTheme(value)
    }
}

fun DataCoordinator.updateLanguage(value: Int) {
    this.language = value
    CoroutineScope(Dispatchers.Default).launch {
        setLanguage(value)
    }
}