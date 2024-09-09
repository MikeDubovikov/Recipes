package com.mdubovikov.recipes.presentation.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.databinding.FragmentDetailsBinding
import com.mdubovikov.recipes.domain.model.MealDetailsModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private val viewModel: DetailsViewModel by viewModels()
    private val args by navArgs<DetailsFragmentArgs>()
    private val mealId by lazy { args.mealId }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.setupMealId(mealId)
                viewModel.meal.collectLatest {
                    with(binding) {
                        when (it) {
                            is Result.Loading -> {
                                progressBar.visibility = View.VISIBLE
                                cvDetails.visibility = View.VISIBLE
                                cvDetailsError.visibility = View.GONE
                            }

                            is Result.Success -> {
                                progressBar.visibility = View.GONE

                                it.data.let { meal ->
                                    setupUI(meal)
                                    binding.cvSaveButton.setOnClickListener {
                                        saveMeal(meal)
                                    }
                                }
                            }

                            is Result.Error -> {
                                cvDetailsError.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                cvDetails.visibility = View.GONE
                                ivMealDetails.setImageResource(R.drawable.ic_error)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveMeal(mealDetailsModel: MealDetailsModel) {
        viewModel.saveMeal(mealDetailsModel)
    }

    private fun setupUI(mealDetailsModel: MealDetailsModel) = binding.apply {

        mealDetails = mealDetailsModel

        lifecycleScope.launch {
            viewModel.imageButton.collect {
                tvSaveOrRemoveButton.setCompoundDrawablesWithIntrinsicBounds(
                    AppCompatResources.getDrawable(requireContext(), it),
                    null,
                    null,
                    null
                )
            }
        }

        lifecycleScope.launch {
            viewModel.saveOrSavedText.collect {
                tvSaveOrRemoveButton.text = getString(it)
            }
        }

        cvYoutubeButton.setOnClickListener {
            val youtubeLink = mealDetailsModel.youtubeLink
            if (!youtubeLink.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.broken_link),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (mealDetailsModel.ingredient1.isNullOrBlank() && mealDetailsModel.measure1.isNullOrBlank()) {
            tvIngredient1.visibility = View.GONE
            tvMeasure1.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient2.isNullOrBlank() && mealDetailsModel.measure2.isNullOrBlank()) {
            tvIngredient2.visibility = View.GONE
            tvMeasure2.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient3.isNullOrBlank() && mealDetailsModel.measure3.isNullOrBlank()) {
            tvIngredient3.visibility = View.GONE
            tvMeasure3.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient4.isNullOrBlank() && mealDetailsModel.measure4.isNullOrBlank()) {
            tvIngredient4.visibility = View.GONE
            tvMeasure4.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient5.isNullOrBlank() && mealDetailsModel.measure5.isNullOrBlank()) {
            tvIngredient5.visibility = View.GONE
            tvMeasure5.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient6.isNullOrBlank() && mealDetailsModel.measure6.isNullOrBlank()) {
            tvIngredient6.visibility = View.GONE
            tvMeasure6.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient7.isNullOrBlank() && mealDetailsModel.measure7.isNullOrBlank()) {
            tvIngredient7.visibility = View.GONE
            tvMeasure7.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient8.isNullOrBlank() && mealDetailsModel.measure8.isNullOrBlank()) {
            tvIngredient8.visibility = View.GONE
            tvMeasure8.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient9.isNullOrBlank() && mealDetailsModel.measure9.isNullOrBlank()) {
            tvIngredient9.visibility = View.GONE
            tvMeasure9.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient10.isNullOrBlank() && mealDetailsModel.measure10.isNullOrBlank()) {
            tvIngredient10.visibility = View.GONE
            tvMeasure10.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient11.isNullOrBlank() && mealDetailsModel.measure11.isNullOrBlank()) {
            tvIngredient11.visibility = View.GONE
            tvMeasure11.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient12.isNullOrBlank() && mealDetailsModel.measure12.isNullOrBlank()) {
            tvIngredient12.visibility = View.GONE
            tvMeasure12.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient13.isNullOrBlank() && mealDetailsModel.measure13.isNullOrBlank()) {
            tvIngredient13.visibility = View.GONE
            tvMeasure13.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient14.isNullOrBlank() && mealDetailsModel.measure14.isNullOrBlank()) {
            tvIngredient14.visibility = View.GONE
            tvMeasure14.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient15.isNullOrBlank() && mealDetailsModel.measure15.isNullOrBlank()) {
            tvIngredient15.visibility = View.GONE
            tvMeasure15.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient16.isNullOrBlank() && mealDetailsModel.measure16.isNullOrBlank()) {
            tvIngredient16.visibility = View.GONE
            tvMeasure16.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient17.isNullOrBlank() && mealDetailsModel.measure17.isNullOrBlank()) {
            tvIngredient17.visibility = View.GONE
            tvMeasure17.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient18.isNullOrBlank() && mealDetailsModel.measure18.isNullOrBlank()) {
            tvIngredient18.visibility = View.GONE
            tvMeasure18.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient19.isNullOrBlank() && mealDetailsModel.measure19.isNullOrBlank()) {
            tvIngredient19.visibility = View.GONE
            tvMeasure19.visibility = View.GONE
        }
        if (mealDetailsModel.ingredient20.isNullOrBlank() && mealDetailsModel.measure20.isNullOrBlank()) {
            tvIngredient20.visibility = View.GONE
            tvMeasure20.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}