package com.arasfon.uni.sem5.drivenext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextTheme
import com.arasfon.uni.sem5.drivenext.presentation.navigation.NavGraph
import com.arasfon.uni.sem5.drivenext.presentation.navigation.Screen
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
