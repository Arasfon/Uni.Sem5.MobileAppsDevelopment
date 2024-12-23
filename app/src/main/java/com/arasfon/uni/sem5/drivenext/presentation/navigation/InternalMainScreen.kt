package com.arasfon.uni.sem5.drivenext.presentation.navigation

import kotlinx.serialization.Serializable

sealed class InternalMainScreen {
    sealed class Home {
        @Serializable
        data object Main : InternalMainScreen()
        @Serializable
        data class Search(val query: String) : InternalMainScreen()
    }

    sealed class Bookmarks {
        @Serializable
        data object Main : InternalMainScreen()
    }

    sealed class Settings {
        @Serializable
        data object Main : InternalMainScreen()
        @Serializable
        data object Profile : InternalMainScreen()
        @Serializable
        data object Bookings : InternalMainScreen()
        @Serializable
        data class BookingDetails(val bookingDetails: com.arasfon.uni.sem5.drivenext.domain.models.bookings.BookingDetails) : InternalMainScreen()
        @Serializable
        data object LessorOnboarding : InternalMainScreen()
        @Serializable
        data object CarAddition : InternalMainScreen()
        @Serializable
        data object CarAdditionSuccess : InternalMainScreen()
    }

    @Serializable
    data class CarDetails(val carDetails: com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails, val sharedTransitionContext: String) : InternalMainScreen()

    @Serializable
    data class CarBooking(val carDetails: com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails, val sharedTransitionContext: String) : InternalMainScreen()

    @Serializable
    data object CarBookingSuccess : InternalMainScreen()
}
