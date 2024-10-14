package com.arasfon.uni.sem5.drivenext.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Main : Screen("main")
    data object NoConnection : Screen("no_connection")
    data object AuthOptions : Screen("auth_options")
    data object SignUp : Screen("sign_up")
}
