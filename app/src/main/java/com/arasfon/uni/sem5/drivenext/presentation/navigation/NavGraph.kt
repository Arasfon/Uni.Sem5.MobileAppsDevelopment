package com.arasfon.uni.sem5.drivenext.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.MainScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.NoConnectionScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.OnboardingScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.SplashScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.auth.AuthOptionsScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.auth.SignInScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.auth.SignUpScreen
import com.arasfon.uni.sem5.drivenext.presentation.ui.screens.auth.SignUpSuccessScreen

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
                onNavigateToAuthOptionsScreen = {
                    navController.navigateClearingBackStack(Screen.Auth.AuthOptions.route)
                },
                onNavigateToMainScreen = {
                    navController.navigateClearingBackStack(Screen.Main.route)
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigateClearingBackStack(Screen.Auth.AuthOptions.route)
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen()
        }
        composable(Screen.NoConnection.route) {
            NoConnectionScreen()
        }
        composable(Screen.Auth.AuthOptions.route) {
            AuthOptionsScreen(
                onNavigateToSignInScreen = {
                    navController.navigate(Screen.Auth.SignIn.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUpScreen = {
                    navController.navigate(Screen.Auth.SignUp.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.Auth.SignIn.route) {
            SignInScreen(
                onSignInSuccessful = {
                    navController.navigateClearingBackStack(Screen.Main.route)
                },
                onPasswordForgot = {
                    TODO("Forgot password")
                },
                onNavigateToSignUpScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.Auth.SignUp.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.Auth.SignUp.route) {
            SignUpScreen(
                onReturnBack = {
                    navController.popBackStack()
                },
                onSignUpSuccessful = {
                    navController.navigateClearingBackStack(Screen.Auth.SignUpSuccess.route)
                }
            )
        }
        composable(Screen.Auth.SignUpSuccess.route) {
            SignUpSuccessScreen(
                onFinish = {
                    navController.navigateClearingBackStack(Screen.Main.route)
                }
            )
        }
    }
}
