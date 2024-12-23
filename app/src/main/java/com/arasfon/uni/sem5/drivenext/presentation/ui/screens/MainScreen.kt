package com.arasfon.uni.sem5.drivenext.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.arasfon.uni.sem5.drivenext.presentation.navigation.InternalMainScreen
import com.arasfon.uni.sem5.drivenext.presentation.navigation.NavTypeMap
import com.arasfon.uni.sem5.drivenext.presentation.navigation.navigateClearingBackStack
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.bookings.BookingDetailsScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.bookings.BookingsScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.bookmarks.MainScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.cars.CarBookingScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.cars.CarBookingSuccessScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.cars.CarDetailsScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.home.MainScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.home.SearchScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.lessoronboarding.CarAdditionScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.lessoronboarding.CarAdditionSuccessScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.lessoronboarding.LessorTermsScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.settings.MainScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.settings.ProfileScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(
    onSignOut: () -> Unit
) {
    var currentPageCategory by rememberSaveable { mutableIntStateOf(0) }
    var isOnCategoryRoot by rememberSaveable { mutableStateOf(false) }

    val internalNavController = rememberNavController()

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = isOnCategoryRoot,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1000f),
                    shadowElevation = 3.dp
                ) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentPageCategory == 0,
                            onClick = {
                                if (currentPageCategory == 0 && isOnCategoryRoot)
                                    return@NavigationBarItem

                                currentPageCategory = 0
                                internalNavController.navigateClearingBackStack(InternalMainScreen.Home.Main)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (currentPageCategory == 0) Icons.Default.Home else Icons.Outlined.Home,
                                    contentDescription = "Домашняя"
                                )
                            },
                        )

                        NavigationBarItem(
                            selected = currentPageCategory == 1,
                            onClick = {
                                if (currentPageCategory == 1 && isOnCategoryRoot)
                                    return@NavigationBarItem

                                currentPageCategory = 1
                                internalNavController.navigateClearingBackStack(InternalMainScreen.Bookmarks.Main)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (currentPageCategory == 1) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "Закладки"
                                )
                            }
                        )

                        NavigationBarItem(
                            selected = currentPageCategory == 2,
                            onClick = {
                                if (currentPageCategory == 2 && isOnCategoryRoot)
                                    return@NavigationBarItem

                                currentPageCategory = 2
                                internalNavController.navigateClearingBackStack(InternalMainScreen.Settings.Main)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (currentPageCategory == 2) Icons.Default.Settings else Icons.Outlined.Settings,
                                    contentDescription = "Настройки"
                                )
                            },
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val layoutDirection = LocalLayoutDirection.current

        Box(
            modifier = Modifier
                .padding(
                    PaddingValues(
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection)
                    )
                )
                .fillMaxSize()
        ) {
            val homeMainScreenListState = rememberLazyListState()
            val bookmarksMainScreenListState = rememberLazyListState()

            SharedTransitionLayout {
                NavHost(
                    navController = internalNavController,
                    startDestination = InternalMainScreen.Home.Main,
                    enterTransition = { fadeIn(animationSpec = tween(200)) },
                    exitTransition = { fadeOut(animationSpec = tween(200)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(200)) },
                    popExitTransition = { fadeOut(animationSpec = tween(200)) }
                ) {
                    composable<InternalMainScreen.Home.Main> {
                        isOnCategoryRoot = true

                        Box(
                            modifier = Modifier
                                .padding(
                                    PaddingValues(
                                        bottom = innerPadding.calculateBottomPadding()
                                    )
                                )
                                .fillMaxSize()
                        ) {
                            MainScreen(
                                listState = homeMainScreenListState,
                                onSearch = { searchQuery ->
                                    internalNavController.navigate(InternalMainScreen.Home.Search(searchQuery))
                                },
                                onCarClick = { carDetails ->
                                    internalNavController.navigate(InternalMainScreen.CarDetails(carDetails, "Home/Main"))
                                },
                                onBookClick = { carDetails ->
                                    internalNavController.navigate(InternalMainScreen.CarBooking(carDetails, "Home/Main"))
                                },
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable
                            )
                        }
                    }
                    composable<InternalMainScreen.Home.Search> { backStackEntry ->
                        val route = backStackEntry.toRoute<InternalMainScreen.Home.Search>()

                        isOnCategoryRoot = false

                        SearchScreen(
                            searchQuery = route.query,
                            onReturnBack = {
                                internalNavController.popBackStack()
                            },
                            onCarClick = { carDetails ->
                                internalNavController.navigate(InternalMainScreen.CarDetails(carDetails, "Home/Search"))
                            },
                            onBookClick = { carDetails ->
                                internalNavController.navigate(InternalMainScreen.CarBooking(carDetails, "Home/Search"))
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable
                        )
                    }
                    composable<InternalMainScreen.Bookmarks.Main> {
                        isOnCategoryRoot = true

                        Box(
                            modifier = Modifier
                                .padding(
                                    PaddingValues(
                                        bottom = innerPadding.calculateBottomPadding()
                                    )
                                )
                                .fillMaxSize()
                        ) {
                            MainScreen(
                                bookmarksMainScreenListState,
                                onCarClick = { carDetails ->
                                    internalNavController.navigate(
                                        InternalMainScreen.CarDetails(
                                            carDetails,
                                            "Bookmarks/Main"
                                        )
                                    )
                                },
                                onBookClick = { carDetails ->
                                    internalNavController.navigate(
                                        InternalMainScreen.CarBooking(
                                            carDetails,
                                            "Bookmarks/Main"
                                        )
                                    )
                                },
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable
                            )
                        }
                    }
                    composable<InternalMainScreen.Settings.Main> {
                        isOnCategoryRoot = true

                        Box(
                            modifier = Modifier
                                .padding(
                                    PaddingValues(
                                        bottom = innerPadding.calculateBottomPadding()
                                    )
                                )
                                .fillMaxSize()
                        ) {
                            MainScreen(
                                onNavigateToProfile = {
                                    internalNavController.navigate(InternalMainScreen.Settings.Profile)
                                },
                                onNavigateToBookings = {
                                    internalNavController.navigate(InternalMainScreen.Settings.Bookings)
                                },
                                onNavigateToLessorOnboarding = {
                                    internalNavController.navigate(InternalMainScreen.Settings.LessorOnboarding)
                                }
                            )
                        }
                    }
                    composable<InternalMainScreen.Settings.Profile> {
                        isOnCategoryRoot = false

                        ProfileScreen(
                            onSignOut = {
                                onSignOut()
                            },
                            onReturnBack = {
                                internalNavController.popBackStack()
                            }
                        )
                    }
                    composable<InternalMainScreen.CarDetails>(
                        typeMap = NavTypeMap
                    ) { backStackEntry ->
                        val route = backStackEntry.toRoute<InternalMainScreen.CarDetails>()

                        isOnCategoryRoot = false

                        CarDetailsScreen(
                            carDetails = route.carDetails,
                            onReturnBack = {
                                internalNavController.popBackStack()
                            },
                            onBook = { carDetails ->
                                internalNavController.navigate(
                                    InternalMainScreen.CarBooking(
                                        carDetails,
                                        route.sharedTransitionContext
                                    )
                                )
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable,
                            sharedTransitionContext = route.sharedTransitionContext
                        )
                    }
                    composable<InternalMainScreen.CarBooking>(
                        typeMap = NavTypeMap
                    ) { backStackEntry ->
                        val route = backStackEntry.toRoute<InternalMainScreen.CarBooking>()

                        isOnCategoryRoot = false

                        CarBookingScreen(
                            carDetails = route.carDetails,
                            onReturnBack = {
                                internalNavController.popBackStack()
                            },
                            onFinish = {
                                internalNavController.navigateClearingBackStack(InternalMainScreen.CarBookingSuccess)
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable,
                            sharedTransitionContext = route.sharedTransitionContext
                        )
                    }
                    composable<InternalMainScreen.CarBookingSuccess> {
                        isOnCategoryRoot = false

                        CarBookingSuccessScreen(
                            onNavigateToHome = {
                                internalNavController.navigateClearingBackStack(InternalMainScreen.Home.Main)
                                currentPageCategory = 0
                            },
                            onNavigateToBookings = {
                                internalNavController.navigateClearingBackStack(InternalMainScreen.Settings.Main)
                                currentPageCategory = 2
                                internalNavController.navigate(InternalMainScreen.Settings.Bookings)
                            }
                        )
                    }
                    composable<InternalMainScreen.Settings.Bookings> {
                        isOnCategoryRoot = false

                        BookingsScreen(
                            onBookingClick = { bookingDetails ->
                                internalNavController.navigate(
                                    InternalMainScreen.Settings.BookingDetails(
                                        bookingDetails
                                    )
                                )
                            },
                            onReturnBack = {
                                internalNavController.popBackStack()
                            }
                        )
                    }
                    composable<InternalMainScreen.Settings.BookingDetails>(
                        typeMap = NavTypeMap
                    ) { backStackEntry ->
                        val route = backStackEntry.toRoute<InternalMainScreen.Settings.BookingDetails>()

                        isOnCategoryRoot = false

                        BookingDetailsScreen(
                            bookingDetails = route.bookingDetails,
                            onReturnBack = {
                                internalNavController.popBackStack()
                            }
                        )
                    }
                    composable<InternalMainScreen.Settings.LessorOnboarding> {
                        isOnCategoryRoot = false

                        LessorTermsScreen(
                            onReturnBack = {
                                internalNavController.popBackStack()
                            },
                            onContinue = {
                                internalNavController.navigate(InternalMainScreen.Settings.CarAddition)
                            }
                        )
                    }
                    composable<InternalMainScreen.Settings.CarAddition> {
                        isOnCategoryRoot = false

                        CarAdditionScreen(
                            onReturnBack = {
                                internalNavController.popBackStack()
                            },
                            onFinish = {
                                internalNavController.navigateClearingBackStack(InternalMainScreen.Settings.CarAdditionSuccess)
                            }
                        )
                    }
                    composable<InternalMainScreen.Settings.CarAdditionSuccess> {
                        isOnCategoryRoot = false

                        CarAdditionSuccessScreen(
                            onNavigateToHome = {
                                currentPageCategory = 0

                                internalNavController.navigateClearingBackStack(InternalMainScreen.Home.Main)
                            }
                        )
                    }
                }
            }
        }
    }
}
