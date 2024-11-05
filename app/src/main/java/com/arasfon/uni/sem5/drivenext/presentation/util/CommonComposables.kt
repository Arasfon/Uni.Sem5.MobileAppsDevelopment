package com.arasfon.uni.sem5.drivenext.presentation.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ErrorOutline
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arasfon.uni.sem5.drivenext.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun<TError> CommonTextField(
    modifier: Modifier = Modifier,
    field: ValidatableField<String, TError>,
    labelText: @Composable () -> String,
    errorText: @Composable (TError?) -> String,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
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
            modifier = Modifier.fillMaxWidth(),
            value = field.value,
            onValueChange = { field.updateValue(it) },
            singleLine = true,
            placeholder = placeholder,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            leadingIcon = leadingIcon,
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
}

@Composable
fun<TError> PasswordTextField(
    modifier: Modifier = Modifier,
    field: ValidatableField<String, TError>,
    labelText: @Composable () -> String,
    errorText: @Composable (TError?) -> String,
    placeholder: @Composable (() -> Unit)? = null,
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
            onValueChange = { field.updateValue(it) },
            singleLine = true,
            placeholder = placeholder,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
