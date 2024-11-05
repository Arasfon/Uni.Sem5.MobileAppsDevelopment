package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.arasfon.uni.sem5.drivenext.R
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextOutlinedButton
import com.arasfon.uni.sem5.drivenext.domain.models.validation.auth.EmailFieldValidationError
import com.arasfon.uni.sem5.drivenext.presentation.util.CommonTextField
import com.arasfon.uni.sem5.drivenext.presentation.util.PasswordTextField
import com.arasfon.uni.sem5.drivenext.presentation.viewmodels.auth.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onSignInSuccessful: () -> Unit,
    onPasswordForgot: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit
) {
    val viewModel: SignInViewModel = hiltViewModel()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val signInLoading by viewModel.signInLoading.collectAsStateWithLifecycle()

    val googleAuthState by viewModel.googleAuthState.collectAsStateWithLifecycle(SignInViewModel.GoogleAuthState.None)

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

    LaunchedEffect(googleAuthState) {
        when (googleAuthState) {
            is SignInViewModel.GoogleAuthState.Error -> {
                Toast.makeText(context,
                    context.getString(R.string.auth_sign_in_google_error), Toast.LENGTH_LONG).show()
            }
            SignInViewModel.GoogleAuthState.None -> { }
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
                    CommonTextField(
                        modifier = Modifier.fillMaxWidth(),
                        field = viewModel.emailField,
                        labelText = { stringResource(R.string.auth_email_label) },
                        errorText = { error ->
                            return@CommonTextField when (error) {
                                EmailFieldValidationError.InvalidEmail -> {
                                    stringResource(R.string.auth_invalid_email)
                                }

                                EmailFieldValidationError.WrongCredentials -> {
                                    stringResource(R.string.auth_sign_in_no_account)
                                }

                                null -> ""
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                        enabled = !signInLoading
                    )

                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth(),
                        field = viewModel.passwordField,
                        labelText = { stringResource(R.string.auth_password_label) },
                        errorText = { error ->
                            stringResource(R.string.auth_password_empty)
                        },
                        enabled = !signInLoading
                    )

                    TextButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { onPasswordForgot() },
                        enabled = false
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
                        onClick = {
                            coroutineScope.launch {
                                val randomNonce = viewModel.getRandomNonce()
                                val credentialRequest = viewModel.getGoogleIdCredentialRequest(randomNonce)
                                val credentialResult = getCredential(context, credentialRequest)

                                if (credentialResult.isFailure)
                                    return@launch

                                val googleIdTokenResult = viewModel.getGoogleIdToken(credentialResult.getOrThrow())

                                if (googleIdTokenResult.isFailure)
                                    return@launch

                                viewModel.signInWithGoogle(googleIdTokenResult.getOrThrow(), randomNonce)
                            }
                        },
                        enabled = !signInLoading
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.google_logo),
                                contentDescription = null,
                                tint = Color.Unspecified
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

suspend fun getCredential(context: Context, credentialRequest: GetCredentialRequest): Result<GetCredentialResponse> {
    val credentialManager = CredentialManager.create(context)

    try {
        val result = credentialManager.getCredential(
            request = credentialRequest,
            context = context
        )

        return Result.success(result)
    } catch (e: GetCredentialException) {
        return Result.failure(e)
    }
}
