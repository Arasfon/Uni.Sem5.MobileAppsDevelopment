package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.lessoronboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arasfon.uni.sem5.drivenext.R
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton

@Composable
fun CarAdditionSuccessScreen(
    onNavigateToHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 24.dp)
                        .height(32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { onNavigateToHome() }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBackIos,
                            contentDescription = "Назад"
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(R.drawable.car_addition_success),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )
                        Text("Ваш автомобиль добавлен", style = MaterialTheme.typography.titleLarge)
                        Text("Поздравляем! Ваш автомобиль одобрен")
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shadowElevation = 1.dp
            ) {
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) {
                    DriveNextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onNavigateToHome()
                        }
                    ) {
                        Text("На главную")
                    }
                }
            }
        }
    }
}
