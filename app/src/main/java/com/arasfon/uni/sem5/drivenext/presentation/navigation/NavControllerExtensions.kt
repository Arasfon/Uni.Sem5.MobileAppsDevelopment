package com.arasfon.uni.sem5.drivenext.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.navigateClearingBackStack(route: String, optionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    this.navigate(route) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true

        optionsBuilder()
    }
}
