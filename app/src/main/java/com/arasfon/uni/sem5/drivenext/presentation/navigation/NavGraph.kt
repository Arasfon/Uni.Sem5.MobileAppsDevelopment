package com.arasfon.uni.sem5.drivenext.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.AuthOptionsScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.MainScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.NoConnectionScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.OnboardingScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.SignInScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.SignUpScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
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
                onNavigateToSignInScreen = {
                    navController.navigate(Screen.SignIn.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUpScreen = {
                    navController.navigate(Screen.SignUp.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.SignIn.route) {
            SignInScreen(
                onSignInSuccessful = {
                    navController.navigateClearingBackStack(Screen.Main.route)
                },
                onPasswordForgot = {
                    TODO("Forgot password")
                },
                onNavigateToSignUpScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignUp.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen()
        }
    }
}
