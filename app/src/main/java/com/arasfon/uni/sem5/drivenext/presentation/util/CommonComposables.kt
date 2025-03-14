package com.arasfon.uni.sem5.drivenext.presentation.util

import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.LocalGasStation
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.arasfon.uni.sem5.drivenext.BuildConfig
import com.arasfon.uni.sem5.drivenext.R
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun<TError> CommonTextField(
    modifier: Modifier = Modifier,
    field: ValidatableField<String, TError>,
    labelText: @Composable () -> String,
    errorText: @Composable (TError?) -> String,
    maxLength: Int? = null,
    hideMaxLength: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    enabled: Boolean = true
) {
    val shouldShowError by field.shouldShowError.collectAsStateWithLifecycle()
    val validationError by field.lastDisplayError.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = labelText(),
            style = MaterialTheme.typography.labelLarge
        )
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = field.value,
            onValueChange = {
                if (maxLength != null)
                    field.updateValue(it.take(maxLength))
                else
                    field.updateValue(it)
            },
            singleLine = singleLine,
            placeholder = placeholder,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            leadingIcon = leadingIcon,
            isError = shouldShowError,
            supportingText = {
                if (maxLength != null && !hideMaxLength) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AnimatedVisibility(shouldShowError) {
                            AnimatedContent(
                                targetState = validationError,
                                label = "ValidationErrorContentAnimation"
                            ) { error ->
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = errorText(error),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.TopEnd),
                                text = "${field.value.length}/$maxLength"
                            )
                        }
                    }
                } else {
                    AnimatedVisibility(shouldShowError) {
                        AnimatedContent(
                            targetState = validationError,
                            label = "ValidationErrorContentAnimation"
                        ) { error ->
                            Text(
                                text = errorText(error),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            },
            trailingIcon = {
                if (shouldShowError) {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@Composable
fun<TError> PasswordTextField(
    modifier: Modifier = Modifier,
    field: ValidatableField<String, TError>,
    labelText: @Composable () -> String,
    errorText: @Composable (TError?) -> String,
    maxLength: Int? = null,
    hideMaxLength: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Default,
    enabled: Boolean = true
) {
    val shouldShowError by field.shouldShowError.collectAsStateWithLifecycle()
    val validationError by field.lastDisplayError.collectAsStateWithLifecycle()

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = labelText(),
            style = MaterialTheme.typography.labelLarge
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = field.value,
            onValueChange = {
                if (maxLength != null)
                    field.updateValue(it.take(maxLength))
                else
                    field.updateValue(it)
            },
            singleLine = true,
            placeholder = placeholder,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction,
                autoCorrectEnabled = false
            ),
            visualTransformation =
            if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible)
                        Icons.Outlined.Visibility
                    else
                        Icons.Outlined.VisibilityOff

                val contentDescription =
                    if (passwordVisible)
                        stringResource(R.string.auth_hide_password)
                    else
                        stringResource(R.string.auth_show_password)

                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    enabled = enabled
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = contentDescription
                    )
                }
            },
            isError = shouldShowError,
            supportingText = {
                if (maxLength != null && !hideMaxLength) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AnimatedVisibility(shouldShowError) {
                            AnimatedContent(
                                targetState = validationError,
                                label = "ValidationErrorContentAnimation"
                            ) { error ->
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = errorText(error),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.TopEnd),
                                text = "${field.value.length}/$maxLength"
                            )
                        }
                    }
                } else {
                    AnimatedVisibility(shouldShowError) {
                        AnimatedContent(
                            targetState = validationError,
                            label = "ValidationErrorContentAnimation"
                        ) { error ->
                            Text(
                                text = errorText(error),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun<TError> DatePickerTextField(
    modifier: Modifier = Modifier,
    field: ValidatableField<LocalDate?, TError>,
    labelText: @Composable () -> String,
    errorText: @Composable (TError?) -> String,
    enabled: Boolean = true
) {
    val shouldShowError by field.shouldShowError.collectAsStateWithLifecycle()
    val validationError by field.lastDisplayError.collectAsStateWithLifecycle()

    val datePickerState = rememberDatePickerState()
    var showModal by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = labelText(),
            style = MaterialTheme.typography.labelLarge
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(field.value) {
                    awaitEachGesture {
                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                        // in the Initial pass to observe events before the text field consumes them
                        // in the Main pass.
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            showModal = true
                        }
                    }
                },
            value = field.value?.toString() ?: "",
            onValueChange = {},
            singleLine = true,
            readOnly = true,
            placeholder = {
                Text("YYYY-MM-DD")
            },
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "Дата"
                )
            },
            isError = shouldShowError,
            supportingText = {
                AnimatedVisibility(shouldShowError) {
                    AnimatedContent(
                        targetState = validationError,
                        label = "ValidationErrorContentAnimation"
                    ) { error ->
                        Text(
                            text = errorText(error),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            trailingIcon = {
                if (shouldShowError) {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = null
                    )
                }
            }
        )
    }

    if (showModal) {
        DatePickerDialog(
            onDismissRequest = { showModal = false },
            confirmButton = {
                TextButton(onClick = {
                    if (datePickerState.selectedDateMillis != null) {
                        field.updateValue(
                            Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                .atZone(ZoneId.of("UTC"))
                                .toLocalDate()
                        )
                    }

                    showModal = false
                }) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { showModal = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CarCard(
    id: Int,
    name: String,
    manufacturer: String,
    pricePerDay: Long,
    photo: Uri = Uri.parse("${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png"),
    onClick: () -> Unit,
    onBookClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionContext: String
) {
    with(sharedTransitionScope) {
        androidx.compose.material3.OutlinedCard(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.padding(24.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(5f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Column {
                                Text(
                                    modifier = Modifier.sharedElement(
                                        sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-name-$id"),
                                        animatedVisibilityScope = animatedContentScope
                                    ),
                                    text = name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    modifier = Modifier.sharedElement(
                                        sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-manufacturer-$id"),
                                        animatedVisibilityScope = animatedContentScope
                                    ),
                                    text = manufacturer,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Row(
                                modifier = Modifier.sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-price-$id"),
                                    animatedVisibilityScope = animatedContentScope
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${formattedNumber(pricePerDay)}₽ ", fontWeight = FontWeight.Medium)
                                Text("в день", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        AsyncImage(
                            modifier = Modifier
                                .weight(4f)
                                .aspectRatio(4f/3f)
                                .fillMaxSize()
                                .sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-photo-$id"),
                                    animatedVisibilityScope = animatedContentScope
                                ),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(photo)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Car photo",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.icon_transmission),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "А/Т",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocalGasStation,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Бензин",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DriveNextButton(
                            modifier = Modifier.weight(1f),
                            onClick = onBookClick
                        ) {
                            Text("Забронировать")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CarBookMiniCard(
    id: Int,
    name: String,
    manufacturer: String,
    pricePerDay: Long,
    photo: Uri = Uri.parse("${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png"),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionContext: String
) {
    with(sharedTransitionScope) {
        androidx.compose.material3.OutlinedCard(
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 36.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(5f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column {
                            Text(
                                modifier = Modifier.sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-name-$id"),
                                    animatedVisibilityScope = animatedContentScope
                                ),
                                text = name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                modifier = Modifier.sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-manufacturer-$id"),
                                    animatedVisibilityScope = animatedContentScope
                                ),
                                text = manufacturer,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(
                            modifier = Modifier.sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-price-$id"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${formattedNumber(pricePerDay)}₽ ", fontWeight = FontWeight.Medium)
                            Text("в день", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    AsyncImage(
                        modifier = Modifier
                            .weight(4f)
                            .aspectRatio(4f/3f)
                            .fillMaxSize()
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "$sharedTransitionContext-CarCard-photo-$id"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photo)
                            .crossfade(false)
                            .build(),
                        contentDescription = "Car photo",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun CarBookMiniCard(
    id: Int,
    name: String,
    manufacturer: String,
    pricePerDay: Long,
    photo: Uri = Uri.parse("${BuildConfig.SUPABASE_URL}/storage/v1/object/public/car_photos/default/first.png")
) {
    androidx.compose.material3.OutlinedCard(
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 36.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(5f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = manufacturer,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${formattedNumber(pricePerDay)}₽ ", fontWeight = FontWeight.Medium)
                        Text("в день", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                AsyncImage(
                    modifier = Modifier
                        .weight(4f)
                        .aspectRatio(4f/3f)
                        .fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Car photo",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
