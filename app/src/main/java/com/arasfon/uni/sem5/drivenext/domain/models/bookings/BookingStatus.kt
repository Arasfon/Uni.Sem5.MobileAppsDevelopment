package com.arasfon.uni.sem5.drivenext.domain.models.bookings

data object BookingStatus {
    val UNKNOWN = 0
    val PENDING = 1
    val APPROVED = 2
    val IN_PROGRESS = 3
    val ENDED = 4
    val CANCELLED = 5
}
