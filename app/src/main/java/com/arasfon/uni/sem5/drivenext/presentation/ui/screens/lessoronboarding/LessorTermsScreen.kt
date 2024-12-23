package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.lessoronboarding

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton

@Composable
fun LessorTermsScreen(
    onReturnBack: () -> Unit,
    onContinue: () -> Unit
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
                        onClick = { onReturnBack() }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBackIos,
                            contentDescription = "Назад"
                        )
                    }

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Стать арендодателем",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Узнайте, что включает в себя становление арендодателем в приложении.\nПрисоединяйтесь к тысячам владельцев, зарабатывающих в приложении.")

                    TermSurface(Icons.Outlined.Settings, "Как это работает", "Размещение бесплатное, вы можете устанавливать свои цены, доступность и правила. Получение и возврат автомобиля просты, а оплата производится быстро после каждой поездки.")
                    TermSurface(Icons.Outlined.Shield, "Политика страхования", "Доступны различные уровни защиты автомобиля на случай непредвиденных ситуаций.")
                    TermSurface(Icons.Outlined.Handshake, "Мы поддержим вас", "От проверки арендаторов до поддержки клиентов — вы всегда можете делиться своим автомобилем с уверенностью.")
                }
            }

            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .fillMaxWidth()
            ) {
                DriveNextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onContinue() }
                ) {
                    Text("Начать")
                }
            }
        }
    }
}

@Composable
fun TermSurface(icon: ImageVector, caption: String, description: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = icon,
                contentDescription = null,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(caption, style = MaterialTheme.typography.titleMedium)

                Text(description)
            }
        }
    }
}
