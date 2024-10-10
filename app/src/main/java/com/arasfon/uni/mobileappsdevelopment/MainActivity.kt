package com.arasfon.uni.mobileappsdevelopment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.arasfon.uni.mobileappsdevelopment.common.theme.DriveNextTheme
import com.arasfon.uni.mobileappsdevelopment.presentation.navigation.NavGraph
import com.arasfon.uni.mobileappsdevelopment.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DriveNextTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()

    NavGraph(
        navController,
        startDestination = Screen.Splash.route
    )
}
