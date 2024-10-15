package com.arasfon.uni.sem5.drivenext.presentation.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.arasfon.uni.sem5.drivenext.R
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextOutlinedButton
import com.arasfon.uni.sem5.drivenext.presentation.viewmodels.SignInViewModel

@Composable
fun SignInScreen(
    onSignInSuccessful: () -> Unit,
    onPasswordForgot: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit
) {
    val viewModel: SignInViewModel = hiltViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current

    val signInLoading by viewModel.signInLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { event ->
                when (event) {
                    SignInViewModel.NavigationEvent.SignInSuccessful -> {
                        onSignInSuccessful()
                    }
                }
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
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
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.auth_sign_in_title),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.auth_sign_in_subtitle),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EmailTextField(
                        modifier = Modifier.fillMaxWidth(),
                        viewModel = viewModel,
                        enabled = !signInLoading
                    )

                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth(),
                        viewModel = viewModel,
                        enabled = !signInLoading
                    )

                    TextButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { onPasswordForgot() }
                    ) {
                        Text(stringResource(R.string.auth_forgot_password_question))
                    }

                    DriveNextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.signIn() },
                        enabled = !signInLoading
                    ) {
                        AnimatedContent(
                            targetState = signInLoading,
                            label = "SignInButtonContentAnimation"
                        ) { signInLoading ->
                            if (!signInLoading) {
                                Text(stringResource(R.string.sign_in))
                            } else {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        }

                    }

                    DriveNextOutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.signInWithGoogle() },
                        enabled = !signInLoading
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(R.drawable.google_logo),
                                contentDescription = null
                            )
                            Text(stringResource(R.string.sign_in_with_google))
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    TextButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { onNavigateToSignUpScreen() }
                    ) {
                        Text(stringResource(R.string.sign_up))
                    }
                }
            }
        }
    }
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel,
    enabled: Boolean = true
) {
    val shouldShowError by viewModel.emailField.shouldShowError.collectAsStateWithLifecycle()
    val validationError by viewModel.emailField.lastDisplayError.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.auth_email_label),
            style = MaterialTheme.typography.labelLarge
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.emailField.value,
            onValueChange = { viewModel.emailField.updateValue(it) },
            singleLine = true,
            placeholder = {
                Text(stringResource(R.string.auth_enter_email))
            },
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = shouldShowError,
            supportingText = {
                AnimatedVisibility(shouldShowError) {
                    AnimatedContent(
                        targetState = validationError,
                        label = "ValidationErrorContentAnimation"
                    ) { error ->
                        when (error) {
                            SignInViewModel.EmailValidationError.InvalidEmail -> {
                                Text(
                                    text = stringResource(R.string.auth_invalid_email),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            SignInViewModel.EmailValidationError.WrongCredentials -> {
                                Text(
                                    text = stringResource(R.string.auth_sign_in_no_account),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            null -> { }
                        }
                    }
                }
            },
            trailingIcon = {
                if (shouldShowError) {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = stringResource(R.string.auth_invalid_email)
                    )
                }
            }
        )
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel,
    enabled: Boolean = true
) {
    val shouldShowError by viewModel.passwordField.shouldShowError.collectAsStateWithLifecycle()

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.auth_password_label),
            style = MaterialTheme.typography.labelLarge
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.passwordField.value,
            onValueChange = { viewModel.passwordField.updateValue(it) },
            singleLine = true,
            placeholder = {
                Text(stringResource(R.string.auth_enter_password))
            },
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
                        imageVector  = image,
                        contentDescription = contentDescription
                    )
                }
            },
            isError = shouldShowError,
            supportingText = {
                AnimatedVisibility(shouldShowError) {
                    Text(
                        text = stringResource(R.string.auth_sign_in_password_empty),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    }
}
