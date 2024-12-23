package com.arasfon.uni.sem5.drivenext.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.arasfon.uni.sem5.drivenext.domain.models.bookings.BookingDetails
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

val NavTypeMap = mapOf(
    typeOf<CarDetails>() to CarDetailsNavType(),
    typeOf<BookingDetails>() to BookingDetailsNavType()
)

class CarDetailsNavType : NavType<CarDetails>(false) {
    override fun get(
        bundle: Bundle,
        key: String
    ): CarDetails? {
        return bundle.getParcelable<CarDetails>(key)
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: CarDetails
    ) {
        bundle.putParcelable(key, value)
    }

    override fun parseValue(value: String): CarDetails {
        return Json.decodeFromString<CarDetails>(value)
    }

    override fun serializeAsValue(value: CarDetails): String {
        return Uri.encode(Json.encodeToString(value))
    }
}


class BookingDetailsNavType : NavType<BookingDetails>(false) {
    override fun get(
        bundle: Bundle,
        key: String
    ): BookingDetails? {
        return bundle.getParcelable<BookingDetails>(key)
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: BookingDetails
    ) {
        bundle.putParcelable(key, value)
    }

    override fun parseValue(value: String): BookingDetails {
        return Json.decodeFromString<BookingDetails>(value)
    }

    override fun serializeAsValue(value: BookingDetails): String {
        return Uri.encode(Json.encodeToString(value))
    }
}
