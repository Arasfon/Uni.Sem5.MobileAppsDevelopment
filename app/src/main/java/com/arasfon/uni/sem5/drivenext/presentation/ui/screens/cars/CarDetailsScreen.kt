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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails
import com.arasfon.uni.sem5.drivenext.presentation.util.formattedNumber

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CarDetailsScreen(
    carDetails: CarDetails,
    onReturnBack: () -> Unit,
    onBook: (carDetails: CarDetails) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionContext: String
) {
    with(sharedTransitionScope) {
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
                            text = "Детали",
                            style = MaterialTheme.typography.titleLarge
                        )

                        var isBookmarked by rememberSaveable { mutableStateOf(carDetails.isBookmarked) }

                        IconButton(
                            onClick = { isBookmarked = !isBookmarked }
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = if (!isBookmarked) Icons.Outlined.FavoriteBorder else Icons.Default.Favorite,
                                contentDescription = if (!isBookmarked) "Добавить в закладки" else "Удалить из закладок"
                            )
                        }
                    }

                    AsyncImage(
                        modifier = Modifier
                            .aspectRatio(4f/3f)
                            .fillMaxWidth()
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-photo-${carDetails.id}"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(Uri.parse(carDetails.photoUri))
                            .crossfade(false)
                            .build(),
                        contentDescription = "Car photo",
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 12.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                modifier = Modifier.sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-name-${carDetails.id}"),
                                    animatedVisibilityScope = animatedContentScope
                                ),
                                text = carDetails.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        HorizontalDivider()

                        Box(
                            modifier = Modifier.padding(vertical = 12.dp),
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

                        Box(
                            modifier = Modifier.padding(vertical = 12.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("Описание", style = MaterialTheme.typography.titleMedium)
                                Text(carDetails.description)
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(90f),
                    shadowElevation = 2.dp,
                    tonalElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .padding(24.dp)
                            .padding(WindowInsets.navigationBars.asPaddingValues()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${formattedNumber(carDetails.pricePerDay)}₽/день",
                            fontWeight = FontWeight.SemiBold
                        )
                        DriveNextButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onBook(carDetails)
                            }
                        ) {
                            Text("Забронировать")
                        }
                    }
                }
            }
        }
    }
}
