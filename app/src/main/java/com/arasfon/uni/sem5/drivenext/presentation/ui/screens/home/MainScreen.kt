package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.arasfon.uni.sem5.drivenext.BuildConfig
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails
import com.arasfon.uni.sem5.drivenext.presentation.util.CarCard

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(
    listState: LazyListState,
    onSearch: (searchQuery: String) -> Unit,
    onCarClick: (carDetails: CarDetails) -> Unit,
    onBookClick: (carDetails: CarDetails) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(100f),
            shadowElevation = 2.dp,
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                var searchQuery by rememberSaveable { mutableStateOf("") }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchQuery,
                    onValueChange = { searchQuery = it.take(50) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant),
                    singleLine = true,
                    placeholder = { Text("Введите марку автомобиля") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchQuery.isNotEmpty())
                                onSearch(searchQuery)
                        }
                    )
                )

                Text(
                    text = "Давайте найдём автомобиль",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
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
                        sharedTransitionContext = "Home/Main"
                    )
                }
            }
        }
    }
}
