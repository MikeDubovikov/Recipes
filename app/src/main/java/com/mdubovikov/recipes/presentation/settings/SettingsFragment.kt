package com.mdubovikov.recipes.presentation.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mdubovikov.recipes.BuildConfig
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.common.data_store.DataCoordinator
import com.mdubovikov.recipes.common.data_store.getLanguage
import com.mdubovikov.recipes.common.data_store.getTheme
import com.mdubovikov.recipes.common.data_store.updateLanguage
import com.mdubovikov.recipes.common.data_store.updateTheme
import com.mdubovikov.recipes.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private var selectedTheme by Delegates.notNull<Int>()
    private var selectedLanguage by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataStoreValues()

        with(binding) {
            changeTheme.setOnClickListener { selectTheme() }
            changeLanguage.setOnClickListener { selectLanguage() }
            about.setOnClickListener { about() }
        }
    }

    private fun getDataStoreValues() {
        lifecycleScope.launch {
            selectedTheme = DataCoordinator.shared.getTheme()
            selectedLanguage = DataCoordinator.shared.getLanguage()
        }
    }

    private fun selectTheme() {
        val themes = resources.getStringArray(R.array.themes)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.choose_theme))
            .setSingleChoiceItems(themes, selectedTheme) { dialog, which ->
                DataCoordinator.shared.updateTheme(which)
                when (which) {
                    0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                getDataStoreValues()
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun selectLanguage() {
        val languages = resources.getStringArray(R.array.languages)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.choose_language))
            .setSingleChoiceItems(languages, selectedLanguage) { dialog, which ->
                when (which) {
                    0 -> {
                        DataCoordinator.shared.updateLanguage(0)
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                    }

                    1 -> {
                        DataCoordinator.shared.updateLanguage(1)
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ru"))
                    }

                    2 -> {
                        DataCoordinator.shared.updateLanguage(2)
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
                    }
                }
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun about() {
        val version = BuildConfig.VERSION_NAME
        val author = BuildConfig.AUTHOR
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.about)
            .setIcon(R.mipmap.ic_launcher_round)
            .setMessage(getString(R.string.version_author, version, author))
            .setPositiveButton(getString(R.string.ok), null)
            .create()
        dialog.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}