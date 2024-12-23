package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.cars

import android.net.Uri
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails
import com.arasfon.uni.sem5.drivenext.presentation.util.CarBookMiniCard
import com.arasfon.uni.sem5.drivenext.presentation.util.formattedNumber

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CarBookingScreen(
    carDetails: CarDetails,
    onReturnBack: () -> Unit,
    onFinish: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionContext: String
) {
    with(sharedTransitionScope) {
        Box(
            modifier = Modifier
                .padding(WindowInsets.systemBars.asPaddingValues())
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
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
                            text = "Оформление аренды",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CarBookMiniCard(
                            id = carDetails.id,
                            name = carDetails.name,
                            manufacturer = carDetails.manufacturer,
                            pricePerDay = carDetails.pricePerDay,
                            photo = Uri.parse(carDetails.photoUri),
                            sharedTransitionScope = sharedTransitionScope,
                            animatedContentScope = animatedContentScope,
                            sharedTransitionContext = sharedTransitionContext
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Начало аренды", style = MaterialTheme.typography.titleMedium)
                            Text("08:00, 27 сентября 2024", textAlign = TextAlign.End)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Конец аренды", style = MaterialTheme.typography.titleMedium)
                            Text("08:00, 30 сентября 2024", textAlign = TextAlign.End)
                        }

                        HorizontalDivider()

                        Box(
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("Адрес нахождения", style = MaterialTheme.typography.titleMedium)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Place,
                                        contentDescription = null
                                    )
                                    Text(carDetails.currentLocation)
                                }
                            }
                        }

                        HorizontalDivider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {
                                Text("Аренда автомобиля ", style = MaterialTheme.typography.titleMedium)
                                Text("x3 дня")
                            }
                            Text("${formattedNumber(carDetails.pricePerDay)}₽/день", textAlign = TextAlign.End)
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
                            Text("${formattedNumber(carDetails.insurancePricePerDay)}₽/день", textAlign = TextAlign.End)
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            tonalElevation = 1.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Итого", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Medium)
                                    Text("${formattedNumber(carDetails.pricePerDay * 3 + carDetails.insurancePricePerDay * 3)}₽", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Medium)
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Возвращаемый депозит", style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline))
                                    Text("${formattedNumber(15000)}₽")
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier.padding(vertical = 24.dp)
                ) {
                    DriveNextButton(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        onClick = { onFinish() }
                    ) {
                        Text("Продолжить")
                    }
                }
            }
        }
    }
}
