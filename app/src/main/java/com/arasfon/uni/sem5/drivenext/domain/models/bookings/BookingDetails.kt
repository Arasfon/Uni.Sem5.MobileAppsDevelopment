package com.arasfon.uni.sem5.drivenext.domain.models.bookings

import android.os.Parcelable
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarBrief
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class BookingDetails(
    val id: Int,
    val car: CarBrief,
    val location: String,
    val bookingStart: Long,
    val bookingEnd: Long,
    val status: Int,
    val pricePerDay: Long,
    val insurancePricePerDay: Long,
    val totalCost: Long
) : Parcelable
