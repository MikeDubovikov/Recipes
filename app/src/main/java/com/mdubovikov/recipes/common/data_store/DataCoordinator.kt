package com.mdubovikov.recipes.common.data_store

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.mdubovikov.recipes.common.Constants.DATA_STORE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataCoordinator {

    companion object {
        val shared = DataCoordinator()
    }

    var context: Context? = null

    var theme: Int = 0
    val defaultTheme: Int = 2

    var language: Int = 0
    val defaultLanguage: Int = 2

    val Context.dataStore by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    fun initialize(context: Context) {
        this.context = context
        CoroutineScope(Dispatchers.Default).launch {
            theme = getTheme()
            language = getLanguage()
        }
    }
}