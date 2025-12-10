package com.unbelievable.justfacts.kotlinmodule.model

data class StoryModel(
    val id: Int = 0,
    val title: String = "",
    val des: String = ""
)

data class StoriesWrapper(
    val Stories: List<StoryModel> = emptyList()  // ✅ Default value is CRUCIAL
)
