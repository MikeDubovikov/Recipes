package com.mdubovikov.recipes.presentation.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mdubovikov.recipes.BuildConfig
import com.mdubovikov.recipes.Language
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.Theme
import com.mdubovikov.recipes.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private val viewModel: SettingsViewModel by viewModels()

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

        setSelectedPosition()

        with(binding) {
            changeTheme.setOnClickListener { selectTheme() }
            changeLanguage.setOnClickListener { selectLanguage() }
            about.setOnClickListener { about() }
        }
    }

    private fun setSelectedPosition() {
        lifecycleScope.launch {
            viewModel.theme.collect { theme ->
                selectedTheme = when (theme) {
                    Theme.LIGHT_THEME -> 0
                    Theme.DARK_THEME -> 1
                    Theme.BY_DEFAULT_THEME -> 2
                    Theme.UNRECOGNIZED -> -1
                }
            }
        }

        lifecycleScope.launch {
            viewModel.language.collect { language ->
                selectedLanguage = when (language) {
                    Language.ENGLISH_LANGUAGE -> 0
                    Language.RUSSIAN_LANGUAGE -> 1
                    Language.BY_DEFAULT_LANGUAGE -> 2
                    Language.UNRECOGNIZED -> -1
                }
            }
        }
    }

    private fun selectTheme() {
        val themes = resources.getStringArray(R.array.themes)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.choose_theme))
            .setSingleChoiceItems(themes, selectedTheme) { dialog, which ->
                when (which) {
                    0 -> viewModel.setTheme(Theme.LIGHT_THEME)

                    1 -> viewModel.setTheme(Theme.DARK_THEME)

                    2 -> viewModel.setTheme(Theme.BY_DEFAULT_THEME)
                }
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
                        viewModel.setLanguage(Language.ENGLISH_LANGUAGE)
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                    }

                    1 -> {
                        viewModel.setLanguage(Language.RUSSIAN_LANGUAGE)
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ru"))
                    }

                    2 -> {
                        viewModel.setLanguage(Language.BY_DEFAULT_LANGUAGE)
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