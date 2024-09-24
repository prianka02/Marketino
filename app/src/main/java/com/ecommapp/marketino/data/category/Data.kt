package com.ecommapp.marketino.data.category

data class Data(
    val id: Int?,
    val name: String?,
    val priority: Any?,
    val status: Int?,
    val sub_categories: List<SubCategory?>?
)