package com.mdubovikov.recipes.di

import com.mdubovikov.recipes.data.repository.MealRepositoryImpl
import com.mdubovikov.recipes.data.repository.UserPreferencesRepositoryImpl
import com.mdubovikov.recipes.domain.repository.MealRepository
import com.mdubovikov.recipes.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoryModule {

    @Binds
    abstract fun bindMealRepository(mealRepositoryImpl: MealRepositoryImpl): MealRepository

    @Binds
    abstract fun bindUserPreferencesRepository(userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl): UserPreferencesRepository
}