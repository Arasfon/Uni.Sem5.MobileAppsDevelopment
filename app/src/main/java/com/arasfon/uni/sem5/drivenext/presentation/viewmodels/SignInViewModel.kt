package com.arasfon.uni.sem5.drivenext.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arasfon.uni.sem5.drivenext.presentation.util.ValidatableField
import com.arasfon.uni.sem5.drivenext.presentation.util.ValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    companion object {
        private val emailRegex: Regex = Regex("""(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""")
    }

    private val _navigationEvent = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    val emailField = ValidatableField(
        initialValue = "",
        validation = { validateEmail(it) },
        scope = viewModelScope
    )

    val passwordField = ValidatableField(
        initialValue = "",
        validation = { validatePassword(it) },
        scope = viewModelScope
    )

    private val _signInLoading = MutableStateFlow(false)
    val signInLoading = _signInLoading.asStateFlow()

    fun signIn() {
        viewModelScope.launch {
            emailField.markStateChanged()
            passwordField.markStateChanged()

            val emailValidationState = emailField.actualValidationState.first()
            val passwordValidationState = passwordField.actualValidationState.first()

            if (emailValidationState !is ValidationState.Valid || passwordValidationState !is ValidationState.Valid) {
                return@launch
            }

            _signInLoading.value = true

            delay(1000)

            if (emailField.value == "test@test.com" && passwordField.value == "test") {
                _navigationEvent.send(NavigationEvent.SignInSuccessful)

                return@launch
            }

            emailField.forceDisplayError(EmailValidationError.WrongCredentials)

            _signInLoading.value = false
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            TODO("Google sign-in")
        }
    }

    private fun validateEmail(email: String): ValidationState<EmailValidationError> {
        return if (email.isNotEmpty() && emailRegex.matches(email))
            ValidationState.Valid
        else
            ValidationState.Invalid(EmailValidationError.InvalidEmail)
    }

    private fun validatePassword(password: String): ValidationState<Unit> {
        return if (password.isNotEmpty())
            ValidationState.Valid
        else
            ValidationState.Invalid(Unit)
    }

    sealed class NavigationEvent {
        data object SignInSuccessful : NavigationEvent()
    }

    sealed class EmailValidationError {
        data object InvalidEmail : EmailValidationError()
        data object WrongCredentials : EmailValidationError()
    }
}
