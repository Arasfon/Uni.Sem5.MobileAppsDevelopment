package com.arasfon.uni.sem5.drivenext.presentation.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arasfon.uni.sem5.drivenext.R

@Composable
fun<TError> CommonTextField(
    modifier: Modifier = Modifier,
    field: ValidatableField<String, TError>,
    labelText: @Composable () -> String,
    errorText: @Composable (TError?) -> String,
    placeholder: @Composable (() -> Unit)? = null,
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
