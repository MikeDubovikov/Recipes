package com.mdubovikov.recipes.di

import com.mdubovikov.recipes.data.data_source.MealDataSource
import com.mdubovikov.recipes.data.local.data_source_local.LocalDataSource
import com.mdubovikov.recipes.data.remote.data_source_remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
abstract class DataSourceModule {

    @Binds
    abstract fun bindMealRemoteDataSource(mealRemoteDataSource: RemoteDataSource): MealDataSource.Remote

    @Binds
    abstract fun bindMealLocalDataSource(mealLocalDataSource: LocalDataSource): MealDataSource.Local
}