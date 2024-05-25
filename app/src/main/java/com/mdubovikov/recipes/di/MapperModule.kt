package com.mdubovikov.recipes.di

import com.mdubovikov.recipes.data.mapper.MealMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
object MapperModule {

    @Provides
    fun provideMealMapper(): MealMapper = MealMapper()
}