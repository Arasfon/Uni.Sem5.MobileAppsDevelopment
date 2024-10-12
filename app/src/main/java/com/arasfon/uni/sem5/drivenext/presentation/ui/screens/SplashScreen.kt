package com.arasfon.uni.sem5.drivenext.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arasfon.uni.sem5.drivenext.R
import com.arasfon.uni.sem5.drivenext.presentation.viewmodels.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToOnboardingScreen: () -> Unit,
    onNavigateToMainScreen: () -> Unit
) {
    val viewModel: SplashViewModel = hiltViewModel()

    val isOnboardingComplete by viewModel.isOnboardingComplete.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LaunchedEffect(Unit) {
            delay(3000)

            if (isOnboardingComplete)
                onNavigateToMainScreen()
            else
                onNavigateToOnboardingScreen()
        }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(stringResource(R.string.splash_subtitle))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(R.drawable.greeting),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
