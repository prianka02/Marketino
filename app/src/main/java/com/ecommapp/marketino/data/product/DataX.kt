package com.ecommapp.marketino.data.product

data class DataX(
    val brand: Brand?,
    val brand_id: Int?,
    val category: Category?,
    val category_id: Int?,
    val color: String?,
    val created_at: String?,
    val creator_id: Int?,
    val creator_type: String?,
    val description: String?,
    val discount: String?,
    val file_manager: FileManager?,
    val id: Int?,
    val main_image: Any?,
    val name: String?,
    val other: String?,
    val price: String?,
    val quantity: Int?,
    val shipping_cost: String?,
    val size: String?,
    val sku: String?,
    val slug: String?,
    val status: Int?,
    val sub_category: SubCategory?,
    val sub_category_id: Int?,
    val updated_at: String?
)