package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.bookmarks

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arasfon.uni.sem5.drivenext.BuildConfig
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails
import com.arasfon.uni.sem5.drivenext.presentation.util.CarCard

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(
    listState: LazyListState,
    onCarClick: (carDetails: CarDetails) -> Unit,
    onBookClick: (carDetails: CarDetails) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Box(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
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
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Избранное",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    state = listState,
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
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
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
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
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
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
                        )
                    }
                    item {
                        val carDetails = CarDetails(
                            id = 3,
                            name = "S 500 Sedan",
                            manufacturer = "Mercedes-Benz",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                            currentLocation = "Авиамоторная ул., 8, стр. 2",
                            description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                            insurancePricePerDay = 300,
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
                        )
                    }
                    item {
                        val carDetails = CarDetails(
                            id = 4,
                            name = "S 500 Sedan",
                            manufacturer = "Mercedes-Benz",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                            currentLocation = "Авиамоторная ул., 8, стр. 2",
                            description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                            insurancePricePerDay = 300,
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
                        )
                    }
                    item {
                        val carDetails = CarDetails(
                            id = 5,
                            name = "S 500 Sedan",
                            manufacturer = "Mercedes-Benz",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                            currentLocation = "Авиамоторная ул., 8, стр. 2",
                            description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                            insurancePricePerDay = 300,
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
                        )
                    }
                    item {
                        val carDetails = CarDetails(
                            id = 6,
                            name = "S 500 Sedan",
                            manufacturer = "Mercedes-Benz",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                            currentLocation = "Авиамоторная ул., 8, стр. 2",
                            description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                            insurancePricePerDay = 300,
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
                        )
                    }
                    item {
                        val carDetails = CarDetails(
                            id = 7,
                            name = "S 500 Sedan",
                            manufacturer = "Mercedes-Benz",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                            currentLocation = "Авиамоторная ул., 8, стр. 2",
                            description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                            insurancePricePerDay = 300,
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
                        )
                    }
                    item {
                        val carDetails = CarDetails(
                            id = 8,
                            name = "S 500 Sedan",
                            manufacturer = "Mercedes-Benz",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                            currentLocation = "Авиамоторная ул., 8, стр. 2",
                            description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                            insurancePricePerDay = 300,
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
                        )
                    }
                    item {
                        val carDetails = CarDetails(
                            id = 9,
                            name = "S 500 Sedan",
                            manufacturer = "Mercedes-Benz",
                            pricePerDay = 2500,
                            photoUri = "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png",
                            currentLocation = "Авиамоторная ул., 8, стр. 2",
                            description = "Tesla Model 3 оснащена электрическим двигателем, который обеспечивает 460 лошадиных сил. Полный привод.",
                            insurancePricePerDay = 300,
                            isBookmarked = true
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
                            sharedTransitionContext = "Bookmarks/Main"
                        )
                    }
                }
            }
        }
    }
}
