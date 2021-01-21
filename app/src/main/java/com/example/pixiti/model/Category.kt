package com.example.pixiti.model

import com.example.pixiti.R

data class Category(
    val name: Int,
    val image: Int,
    val label: String
) {
    companion object {
        fun createCategoryList(): List<Category> {
            return listOf(
                Category(R.string.label_category_animals, R.drawable.ic_animals_cat, "animals"),
                Category(R.string.label_category_backgrounds, R.drawable.ic_background_cat, "backgrounds"),
                Category(R.string.label_category_buildings, R.drawable.ic_buildings_cat, "buildings"),
                Category(R.string.label_category_business, R.drawable.ic_business_cat, "business"),
                Category(R.string.label_category_computer, R.drawable.ic_computer_cat, "computer"),
                Category(R.string.label_category_education, R.drawable.ic_education_cat, "education"),
                Category(R.string.label_category_fashion, R.drawable.ic_fashion_cat, "fashion"),
                Category(R.string.label_category_feelings, R.drawable.ic_feelings_cat, "feelings"),
                Category(R.string.label_category_food, R.drawable.ic_food_cat, "food"),
                Category(R.string.label_category_health, R.drawable.ic_health_cat, "health"),
                Category(R.string.label_category_industry, R.drawable.ic_industry_cat, "industry"),
                Category(R.string.label_category_nature, R.drawable.ic_nature_cat, "nature"),
                Category(R.string.label_category_people, R.drawable.ic_people_cat, "people"),
                Category(R.string.label_category_places, R.drawable.ic_places_cat, "places"),
                Category(R.string.label_category_religion, R.drawable.ic_religion_cat, "religion"),
                Category(R.string.label_category_science, R.drawable.ic_science_cat, "science"),
                Category(R.string.label_category_sports, R.drawable.ic_sports_cat, "sports"),
                Category(R.string.label_category_transportation, R.drawable.ic_transportation_cat, "transportation"),
                Category(R.string.label_category_travel, R.drawable.ic_travel_cat, "travel")
            )
        }
    }
}