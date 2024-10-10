package com.arasfon.uni.mobileappsdevelopment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arasfon.uni.mobileappsdevelopment.presentation.ui.screens.MainScreen
import com.arasfon.uni.mobileappsdevelopment.presentation.ui.screens.OnboardingScreen
import com.arasfon.uni.mobileappsdevelopment.presentation.ui.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController, startDestination = startDestination) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboardingScreen = {
                    navController.navigateClearingBackStack(Screen.Onboarding.route)
                },
                onNavigateToMainScreen = {
                    navController.navigateClearingBackStack(Screen.Main.route)
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen()
        }
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
