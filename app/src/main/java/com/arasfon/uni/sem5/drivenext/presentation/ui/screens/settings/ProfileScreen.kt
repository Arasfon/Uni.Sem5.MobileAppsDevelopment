package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.arasfon.uni.sem5.drivenext.BuildConfig
import com.arasfon.uni.sem5.drivenext.presentation.util.noIndicationClickable
import com.arasfon.uni.sem5.drivenext.presentation.viewmodels.settings.ProfileViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit,
    onReturnBack: () -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()

    Box(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
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
                    text = "Профиль",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val profilePhotoPickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
                        if (uri != null) {
                            //viewModel.profilePhotoField.updateValue(uri)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(144.dp)
                            .noIndicationClickable(onClick = {
                                profilePhotoPickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                            })
                    ) {
                        AnimatedContent(
                            targetState = "", //viewModel.profilePhotoField.value,
                            label = "ProfilePhotoContentAnimation"
                        ) { value ->
                            if (value == null) {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "Загрузить фото профиля",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            else {
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        //.data(viewModel.profilePhotoField.value)
                                        .data("${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Фото профиля",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(28.dp)
                                .align(Alignment.BottomEnd)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedContent(
                                targetState = false, //viewModel.profilePhotoField.value == null,
                                label = "ProfilePhotoButtonContentAnimation"
                            ) { isUrlNull ->
                                if (isUrlNull) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = Icons.Outlined.Add,
                                        contentDescription = "Загрузить фото профиля",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                                else {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = "Изменить фото профиля",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(viewModel.getUserFullName() ?: "", style = MaterialTheme.typography.titleMedium)

                        val monthInPrepositionalCase = mapOf(
                            1 to "январе",
                            2 to "феврале",
                            3 to "марте",
                            4 to "апреле",
                            5 to "мае",
                            6 to "июне",
                            7 to "июле",
                            8 to "августе",
                            9 to "сентябре",
                            10 to "октябре",
                            11 to "ноябре",
                            12 to "декабре"
                        )

                        val localDateTime = (viewModel.getUserCreationDate() ?: Instant.fromEpochSeconds(0)).toLocalDateTime(TimeZone.currentSystemDefault())
                        val formattedMonth: String = monthInPrepositionalCase[localDateTime.month.value]!!

                        Text("Присоединился в $formattedMonth ${localDateTime.year}")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {}
                                )
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Электронная почта", style = MaterialTheme.typography.titleMedium)
                                    Text(viewModel.getUserEmail() ?: "Нет", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        HorizontalDivider()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {}
                                )
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Пароль", style = MaterialTheme.typography.titleMedium)
                                    Text("Изменить пароль", color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }

                        HorizontalDivider()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {}
                                )
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Пол", style = MaterialTheme.typography.titleMedium)
                                    Text("Мужской", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        HorizontalDivider()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {}
                                )
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Google", style = MaterialTheme.typography.titleMedium)
                                    Text(viewModel.getUserGoogleEmail() ?: "Нет", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        HorizontalDivider()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        viewModel.signOut()
                                        onSignOut()
                                    }
                                )
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    text = "Выйти из профиля",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
