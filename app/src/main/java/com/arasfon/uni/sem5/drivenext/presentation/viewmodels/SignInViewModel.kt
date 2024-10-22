package com.arasfon.uni.sem5.drivenext.presentation.viewmodels

import android.util.Log
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arasfon.uni.sem5.drivenext.BuildConfig
import com.arasfon.uni.sem5.drivenext.domain.models.RandomNonce
import com.arasfon.uni.sem5.drivenext.domain.models.validation.EmailFieldValidationError
import com.arasfon.uni.sem5.drivenext.domain.models.validation.ValidationResult
import com.arasfon.uni.sem5.drivenext.domain.usecases.GenerateRandomNonceUseCase
import com.arasfon.uni.sem5.drivenext.domain.usecases.ValidateEmailFieldUseCase
import com.arasfon.uni.sem5.drivenext.domain.usecases.ValidatePasswordFieldUseCase
import com.arasfon.uni.sem5.drivenext.presentation.util.ValidatableField
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase,
    private val generateRandomNonceUseCase: GenerateRandomNonceUseCase
) : ViewModel() {
    private val _navigationEvent = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    val emailField = ValidatableField(
        initialValue = "",
        validation = { validateEmailFieldUseCase(it) },
        scope = viewModelScope
    )

    val passwordField = ValidatableField(
        initialValue = "",
        validation = { validatePasswordFieldUseCase(it) },
        scope = viewModelScope
    )

    private val _signInLoading = MutableStateFlow(false)
    val signInLoading = _signInLoading.asStateFlow()

    private val _googleAuthState = MutableSharedFlow<GoogleAuthState>()
    val googleAuthState = _googleAuthState.asSharedFlow()

    fun signIn() {
        viewModelScope.launch {
            emailField.markStateChanged()
            passwordField.markStateChanged()

            val emailValidationState = emailField.actualValidationState.first()
            val passwordValidationState = passwordField.actualValidationState.first()

            if (emailValidationState !is ValidationResult.Valid || passwordValidationState !is ValidationResult.Valid) {
                return@launch
            }

            _signInLoading.value = true

            try {
                supabaseClient.auth.signInWith(Email) {
                    email = emailField.value
                    password = passwordField.value
                }
            } catch (e: RestException) {
                emailField.forceDisplayError(EmailFieldValidationError.WrongCredentials)
            } catch (e: HttpRequestTimeoutException) {
                // TODO
            } catch (e: HttpRequestException) {
                // TODO
            }

            _signInLoading.value = false
        }
    }

    fun getGoogleIdCredentialRequest(randomNonce: RandomNonce): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLEID_SERVERCLIENTID)
            .setNonce(randomNonce.hashedNonce)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return request
    }

    fun getGoogleIdToken(credentialResponse: GetCredentialResponse): Result<String> {
        try {
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credentialResponse.credential.data)

            val googleIdToken = googleIdTokenCredential.idToken

            return Result.success(googleIdToken)
        } catch (e: GoogleIdTokenParsingException) {
            return Result.failure(e)
        }
    }

    fun signInWithGoogle(googleIdToken: String, randomNonce: RandomNonce) {
        viewModelScope.launch {
            _signInLoading.value = true

            try {
                supabaseClient.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = randomNonce.rawNonce
                }

                _navigationEvent.send(NavigationEvent.SignInSuccessful)
            } catch (e: Exception) {
                Log.e("SignIn-ViewModel", e.toString())
                _googleAuthState.emit(GoogleAuthState.Error(e))
            }

            _signInLoading.value = false
        }
    }

    fun getRandomNonce(): RandomNonce {
        return generateRandomNonceUseCase()
    }

    sealed class NavigationEvent {
        data object SignInSuccessful : NavigationEvent()
    }

    sealed class GoogleAuthState {
        data object None : GoogleAuthState()
        data class Error(val exception: Exception) : GoogleAuthState()
    }
}
