package com.ecommapp.marketino.data.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int?,
    val name: String?,
    val priority: Int?,
    val status: Int?
): Parcelable