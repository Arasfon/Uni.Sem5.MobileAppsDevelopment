package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.bookings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arasfon.uni.sem5.drivenext.BuildConfig
import com.arasfon.uni.sem5.drivenext.domain.models.bookings.BookingDetails
import com.arasfon.uni.sem5.drivenext.domain.models.bookings.BookingStatus
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarBrief
import com.arasfon.uni.sem5.drivenext.presentation.util.formattedTicks
import com.arasfon.uni.sem5.drivenext.presentation.util.formattedTicksDate

@Composable
fun BookingsScreen(
    onBookingClick: (bookingDetails: BookingDetails) -> Unit,
    onReturnBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 24.dp)
                        .height(32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { onReturnBack() }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBackIos,
                            contentDescription = "Назад"
                        )
                    }

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Мои бронирования",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                val bookings = listOf(
                    BookingDetails(
                        id = 175,
                        car = CarBrief(
                            id = 0,
                            name = "S 500 Sedan",
                            manufacturer = "Mercedes-Benz",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png"
                        ),
                        location = "Авиамоторная ул., 8, стр. 2",
                        bookingStart = 100000,
                        bookingEnd = 2000000,
                        status = BookingStatus.APPROVED,
                        pricePerDay = 2500,
                        insurancePricePerDay = 300,
                        totalCost = 8400
                    ),
                    BookingDetails(
                        id = 176,
                        car = CarBrief(
                            id = 0,
                            name = "3 Series",
                            manufacturer = "BMW",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png"
                        ),
                        location = "Авиамоторная ул., 8, стр. 2",
                        bookingStart = 100000,
                        bookingEnd = 2000000,
                        status = BookingStatus.ENDED,
                        pricePerDay = 2500,
                        insurancePricePerDay = 300,
                        totalCost = 8400
                    ),
                    BookingDetails(
                        id = 177,
                        car = CarBrief(
                            id = 0,
                            name = "A4",
                            manufacturer = "Audi",
                            pricePerDay = 3500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png"
                        ),
                        location = "Авиамоторная ул., 8, стр. 2",
                        bookingStart = 1000000,
                        bookingEnd = 12000000,
                        status = BookingStatus.CANCELLED,
                        pricePerDay = 3500,
                        insurancePricePerDay = 300,
                        totalCost = 11400
                    )
                )

                LazyColumn {
                    for (i in 0..<bookings.size) {
                        val booking = bookings[i]

                        val statusText = when (booking.status) {
                            BookingStatus.PENDING -> "В обработке"
                            BookingStatus.APPROVED -> "Подтверждено"
                            BookingStatus.ENDED -> "Завершено"
                            BookingStatus.CANCELLED -> "Отменено"
                            else -> "Неизвестно"
                        } + ", " + when (booking.status) {
                            BookingStatus.PENDING -> "Начало: " + formattedTicks(booking.bookingStart)
                            BookingStatus.APPROVED -> "Начало: " + formattedTicks(booking.bookingStart)
                            BookingStatus.ENDED -> formattedTicksDate(booking.bookingStart)
                            BookingStatus.CANCELLED -> formattedTicksDate(booking.bookingStart)
                            else -> "—"
                        }

                        item {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            onClick = { onBookingClick(booking) }
                                        )
                                ) {
                                    Box(
                                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text("${booking.car.manufacturer} ${booking.car.name}", style = MaterialTheme.typography.titleMedium)
                                            Text(statusText, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }

                                if (i != bookings.size - 1) {
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
