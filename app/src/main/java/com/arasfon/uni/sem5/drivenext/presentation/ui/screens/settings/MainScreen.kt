package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.CarRental
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.arasfon.uni.sem5.drivenext.BuildConfig
import com.arasfon.uni.sem5.drivenext.presentation.viewmodels.settings.ProfileViewModel

@Composable
fun MainScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToLessorOnboarding: () -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()

    Box(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .height(32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Настройки",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable(
                                onClick = { onNavigateToProfile() }
                            )
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Фото профиля",
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(viewModel.getUserFullName() ?: "", style = MaterialTheme.typography.titleMedium)
                                    Text(viewModel.getUserEmail() ?: "", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            Icon(
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.CenterEnd),
                                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SettingsMenuItem(Icons.Outlined.CarRental, "Мои бронирования") { onNavigateToBookings() }

                    Spacer(modifier = Modifier.height(8.dp))

                    SettingsMenuItem(Icons.Outlined.LightMode, "Тема") {}
                    SettingsMenuItem(Icons.Outlined.Notifications, "Уведомления") {}
                    SettingsMenuItem(Icons.Outlined.Money, "Подключить свой автомобиль") { onNavigateToLessorOnboarding() }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    SettingsMenuItem(Icons.AutoMirrored.Outlined.HelpOutline, "Помощь") {}
                    SettingsMenuItem(Icons.Outlined.Mail, "Пригласи друга") {}
                }
            }
        }
    }
}

@Composable
fun SettingsMenuItem(
    icon: ImageVector,
    textContent: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = { onClick() }
            )
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )

                Text(textContent)
            }

            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterEnd),
                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                contentDescription = null
            )
        }
    }
}
