package com.arasfon.uni.sem5.drivenext.domain.models.bookings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingBrief(
    val carName: String,
    val carManufacturer: String,
    val status: Int,
    val bookingStart: Long,
    val bookingEnd: Long
) : Parcelable
