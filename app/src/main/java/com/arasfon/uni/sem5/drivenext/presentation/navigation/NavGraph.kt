package com.arasfon.uni.sem5.drivenext.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.AuthOptionsScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.MainScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.NoConnectionScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.OnboardingScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
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
            OnboardingScreen(
                onFinish = {
                    navController.navigateClearingBackStack(Screen.AuthOptions.route)
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen()
        }
        composable(Screen.NoConnection.route) {
            NoConnectionScreen()
        }
        composable(Screen.AuthOptions.route) {
            AuthOptionsScreen(
                onNavigateToSignInScreen = { },
                onNavigateToSignUpScreen = { }
            )
        }
    }
}
