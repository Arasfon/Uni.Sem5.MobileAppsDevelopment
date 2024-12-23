package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.bookings

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import com.arasfon.uni.sem5.drivenext.domain.models.bookings.BookingDetails
import com.arasfon.uni.sem5.drivenext.domain.models.bookings.BookingStatus
import com.arasfon.uni.sem5.drivenext.presentation.util.CarBookMiniCard
import com.arasfon.uni.sem5.drivenext.presentation.util.formattedNumber
import com.arasfon.uni.sem5.drivenext.presentation.util.formattedTicks

@Composable
fun BookingDetailsScreen(
    bookingDetails: BookingDetails,
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
                        text = "Бронирование #${bookingDetails.id}",
                        style = MaterialTheme.typography.titleLarge
                    )
                }


                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CarBookMiniCard(
                        id = bookingDetails.car.id,
                        name = bookingDetails.car.name,
                        manufacturer = bookingDetails.car.manufacturer,
                        pricePerDay = bookingDetails.car.pricePerDay,
                        photo = Uri.parse(bookingDetails.car.photoUri)
                    )

                    val statusText = when (bookingDetails.status) {
                        BookingStatus.PENDING -> "В обработке"
                        BookingStatus.APPROVED -> "Подтверждено"
                        BookingStatus.ENDED -> "Завершено"
                        BookingStatus.CANCELLED -> "Отменено"
                        else -> "Неизвестно"
                    }

                    PropertyText("Адрес нахождения", bookingDetails.location)
                    PropertyText("Начало аренды", formattedTicks(bookingDetails.bookingStart))
                    PropertyText("Конец аренды", formattedTicks(bookingDetails.bookingEnd))
                    PropertyText("Статус аренды", statusText)

                    HorizontalDivider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Text("Аренда ", style = MaterialTheme.typography.titleMedium)
                            Text("x3 дня")
                        }
                        Text("${formattedNumber(bookingDetails.pricePerDay)}₽/день", textAlign = TextAlign.End)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Text("Страховка ", style = MaterialTheme.typography.titleMedium)
                            Text("x3 дня")
                        }
                        Text("${formattedNumber(bookingDetails.insurancePricePerDay)}₽/день", textAlign = TextAlign.End)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Итого", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Medium)
                        Text("${formattedNumber(bookingDetails.pricePerDay * 3 + bookingDetails.insurancePricePerDay * 3)}₽", textAlign = TextAlign.End, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Medium)
                    }
                }
            }

            if (bookingDetails.status == BookingStatus.PENDING ||
                bookingDetails.status == BookingStatus.APPROVED ||
                bookingDetails.status == BookingStatus.IN_PROGRESS) {
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) {
                    DriveNextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onReturnBack()
                        }
                    ) {
                        Text("Отменить бронирование")
                    }
                }
            }
        }
    }
}

@Composable
fun PropertyText(caption: String, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(caption, style = MaterialTheme.typography.titleMedium)
        Text(text, textAlign = TextAlign.End)
    }
}
