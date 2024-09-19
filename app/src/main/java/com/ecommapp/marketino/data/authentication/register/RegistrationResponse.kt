package com.ecommapp.marketino.data.authentication.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegistrationResponse(
    val message: String?,
    val status: Int?
): Parcelable