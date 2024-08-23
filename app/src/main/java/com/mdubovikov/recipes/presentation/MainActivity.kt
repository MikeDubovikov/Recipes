package com.mdubovikov.recipes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.common.data_store.DataCoordinator
import com.mdubovikov.recipes.common.data_store.getLanguage
import com.mdubovikov.recipes.common.data_store.getTheme
import com.mdubovikov.recipes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding
        get() = _binding ?: throw IllegalStateException("Activity $this binding cannot be accessed")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.savedFragment,
                R.id.settingsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        checkDataStoreValues()

    }

    private fun checkDataStoreValues() {
        DataCoordinator.shared.initialize(context = baseContext)

        lifecycleScope.launch {
            when (DataCoordinator.shared.getTheme()) {
                0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

            when (DataCoordinator.shared.getLanguage()) {
                0 -> AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                1 -> AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ru"))
                2 -> AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.mainContainer)
        return navController.navigateUp()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}