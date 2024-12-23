package com.arasfon.uni.sem5.drivenext.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Main : Screen("main")
    data object NoConnection : Screen("no_connection")

    sealed class Auth {
        data object AuthOptions : Screen("auth_options")
        data object SignIn : Screen("auth_sign_in")
        data object SignUp : Screen("auth_sign_up")
        data object SignUpSuccess : Screen("auth_sign_up_success")
    }
}
