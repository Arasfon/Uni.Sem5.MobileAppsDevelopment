package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.arasfon.uni.sem5.drivenext.BuildConfig
import com.arasfon.uni.sem5.drivenext.R
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails
import com.arasfon.uni.sem5.drivenext.presentation.util.CarCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    onReturnBack: () -> Unit,
    onCarClick: (carDetails: CarDetails) -> Unit,
    onBookClick: (carDetails: CarDetails) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    var isLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)

        isLoaded = true
    }

    AnimatedContent(
        targetState = isLoaded,
        label = "CoreAnimatedContent"
    ) { isLoaded ->
        if (!isLoaded) {
            Box(
                modifier = Modifier
                    .padding(WindowInsets.systemBars.asPaddingValues())
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        modifier = Modifier.height(128.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.home_search_loading),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )

                    Text("Ищем подходящие автомобили...")

                    LinearProgressIndicator(
                        modifier = Modifier.width(128.dp)
                    )
                }
            }
        }
        else {
            Box(
                modifier = Modifier
                    .padding(WindowInsets.systemBars.asPaddingValues())
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
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
                            text = "Результаты поиска",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(24.dp)
                    ) {
                        item {
                            val carDetails = CarDetails(
                                id = 0,
                                name = "S 500 Sedan",
                                manufacturer = "Mercedes-Benz",
                                pricePerDay = 2500,
                                photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                                currentLocation = "Авиамоторная ул., 8, стр. 2",
                                description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                                insurancePricePerDay = 300,
                                isBookmarked = false
                            )

                            CarCard(
                                id = carDetails.id,
                                name = carDetails.name,
                                manufacturer = carDetails.manufacturer,
                                pricePerDay = carDetails.pricePerDay,
                                onClick = { onCarClick(carDetails) },
                                onBookClick = { onBookClick(carDetails) },
                                sharedTransitionScope = sharedTransitionScope,
                                animatedContentScope = animatedContentScope,
                                sharedTransitionContext = "Search/Main"
                            )
                        }
                        item {
                            val carDetails = CarDetails(
                                id = 1,
                                name = "S 500 Sedan",
                                manufacturer = "Mercedes-Benz",
                                pricePerDay = 2500,
                                photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                                currentLocation = "Авиамоторная ул., 8, стр. 2",
                                description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                                insurancePricePerDay = 300,
                                isBookmarked = false
                            )

                            CarCard(
                                id = carDetails.id,
                                name = carDetails.name,
                                manufacturer = carDetails.manufacturer,
                                pricePerDay = carDetails.pricePerDay,
                                onClick = { onCarClick(carDetails) },
                                onBookClick = { onBookClick(carDetails) },
                                sharedTransitionScope = sharedTransitionScope,
                                animatedContentScope = animatedContentScope,
                                sharedTransitionContext = "Search/Main"
                            )
                        }
                        item {
                            val carDetails = CarDetails(
                                id = 2,
                                name = "S 500 Sedan",
                                manufacturer = "Mercedes-Benz",
                                pricePerDay = 2500,
                                photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                                currentLocation = "Авиамоторная ул., 8, стр. 2",
                                description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                                insurancePricePerDay = 300,
                                isBookmarked = false
                            )

                            CarCard(
                                id = carDetails.id,
                                name = carDetails.name,
                                manufacturer = carDetails.manufacturer,
                                pricePerDay = carDetails.pricePerDay,
                                onClick = { onCarClick(carDetails) },
                                onBookClick = { onBookClick(carDetails) },
                                sharedTransitionScope = sharedTransitionScope,
                                animatedContentScope = animatedContentScope,
                                sharedTransitionContext = "Search/Main"
                            )
                        }
                    }
                }
            }
        }
    }
}
