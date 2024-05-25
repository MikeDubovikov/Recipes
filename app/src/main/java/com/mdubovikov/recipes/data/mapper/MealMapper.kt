package com.mdubovikov.recipes.data.mapper

import com.mdubovikov.recipes.data.local.entity.MealEntity
import com.mdubovikov.recipes.data.remote.dto.CategoryDto
import com.mdubovikov.recipes.data.remote.dto.MealDetailsDto
import com.mdubovikov.recipes.data.remote.dto.MealDto
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.domain.model.MealDetailsModel
import com.mdubovikov.recipes.domain.model.MealModel

class MealMapper {

    private fun mapMealDtoToMealModel(dto: MealDto) = MealModel(
        id = dto.id,
        name = dto.name,
        image = dto.image
    )

    fun mapListMealDtoToListMealModel(dto: List<MealDto>?): List<MealModel> =
        dto?.map { mealDto -> mapMealDtoToMealModel(mealDto) } ?: listOf(MealModel())

    fun mapMealModelToMealEntity(domainModel: MealModel) = MealEntity(
        id = domainModel.id,
        name = domainModel.name,
        image = domainModel.image,
        isSaved = domainModel.isSaved
    )

    fun mapMealEntityToMealModel(entity: MealEntity) = MealModel(
        id = entity.id,
        name = entity.name,
        image = entity.image,
        isSaved = entity.isSaved
    )

    fun mapMealDetailsDtoToMealDetailsModel(dto: MealDetailsDto) = MealDetailsModel(
        id = dto.id,
        area = dto.area,
        name = dto.name,
        image = dto.image,
        category = dto.category,
        youtubeLink = dto.youtubeLink,
        instructions = dto.instructions,
        ingredient1 = dto.ingredient1,
        ingredient2 = dto.ingredient2,
        ingredient3 = dto.ingredient3,
        ingredient4 = dto.ingredient4,
        ingredient5 = dto.ingredient5,
        ingredient6 = dto.ingredient6,
        ingredient7 = dto.ingredient7,
        ingredient8 = dto.ingredient8,
        ingredient9 = dto.ingredient9,
        ingredient10 = dto.ingredient10,
        ingredient11 = dto.ingredient11,
        ingredient12 = dto.ingredient12,
        ingredient13 = dto.ingredient13,
        ingredient14 = dto.ingredient14,
        ingredient15 = dto.ingredient15,
        ingredient16 = dto.ingredient16,
        ingredient17 = dto.ingredient17,
        ingredient18 = dto.ingredient18,
        ingredient19 = dto.ingredient19,
        ingredient20 = dto.ingredient20,
        measure1 = dto.measure1,
        measure2 = dto.measure2,
        measure3 = dto.measure3,
        measure4 = dto.measure4,
        measure5 = dto.measure5,
        measure6 = dto.measure6,
        measure7 = dto.measure7,
        measure8 = dto.measure8,
        measure9 = dto.measure9,
        measure10 = dto.measure10,
        measure11 = dto.measure11,
        measure12 = dto.measure12,
        measure13 = dto.measure13,
        measure14 = dto.measure14,
        measure15 = dto.measure15,
        measure16 = dto.measure16,
        measure17 = dto.measure17,
        measure18 = dto.measure18,
        measure19 = dto.measure19,
        measure20 = dto.measure20
    )

    fun mapMealDetailsModelToMealModel(mealDetailsModel: MealDetailsModel) = MealModel(
        id = mealDetailsModel.id,
        name = mealDetailsModel.name,
        image = mealDetailsModel.image
    )

    private fun mapCategoryDtoToCategoryModel(dto: CategoryDto) = CategoryModel(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        image = dto.image
    )

    fun mapListCategoryDtoToListCategoryModel(dto: List<CategoryDto>): List<CategoryModel> =
        dto.map { categoryEntity -> mapCategoryDtoToCategoryModel(categoryEntity) }
}