package com.arasfon.uni.sem5.drivenext.domain.models.cars

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CarBrief(
    val id: Int,
    val name: String,
    val manufacturer: String,
    val pricePerDay: Long,
    val photoUri: String
) : Parcelable
