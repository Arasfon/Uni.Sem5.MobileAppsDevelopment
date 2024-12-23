package com.arasfon.uni.sem5.drivenext.presentation.viewmodels.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arasfon.uni.sem5.drivenext.domain.models.Sex
import com.arasfon.uni.sem5.drivenext.domain.models.validation.ValidationResult
import com.arasfon.uni.sem5.drivenext.presentation.util.ValidatableField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.text.isNotEmpty

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val STAGE_COUNT = 3
        const val STAGE_LOGIN_INFO = 0
        const val STAGE_PERSONAL_DETAILS = 1
        const val STAGE_LICENSE_AND_PHOTOS = 2
    }

    private val _navigationEvent = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _currentStage = MutableStateFlow(STAGE_LOGIN_INFO)
    val currentStage: StateFlow<Int> = _currentStage.asStateFlow()

    val emailField = ValidatableField(
        initialValue = "",
        validation = {
            return@ValidatableField if (it.isNotEmpty())
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val passwordField = ValidatableField(
        initialValue = "",
        validation = {
            return@ValidatableField if (it.isNotEmpty())
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val repeatPasswordField = ValidatableField(
        initialValue = "",
        validation = {
            return@ValidatableField if (it.isEmpty())
                ValidationResult.Invalid(Unit)
            else if (it != passwordField.value)
                ValidationResult.Invalid(Unit)
            else
                ValidationResult.Valid
        },
        scope = viewModelScope,
        dependencies = listOf(passwordField.valueFlow)
    )

    val acceptTermsField = ValidatableField(
        initialValue = false,
        validation = {
            return@ValidatableField if (it)
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val surnameField = ValidatableField(
        initialValue = "",
        validation = {
            return@ValidatableField if (it.isNotEmpty())
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val nameField = ValidatableField(
        initialValue = "",
        validation = {
            return@ValidatableField if (it.isNotEmpty())
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val patronymicField = ValidatableField(
        initialValue = "",
        validation = {
            return@ValidatableField if (it.isNotEmpty())
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val birthdayField = ValidatableField<LocalDate?, Unit>(
        initialValue = null,
        validation = {
            return@ValidatableField if (it != null)
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val sexField = ValidatableField(
        initialValue = Sex.NOT_SET,
        validation = {
            return@ValidatableField if (it == Sex.NOT_SET)
                ValidationResult.Invalid(Unit)
            else
                ValidationResult.Valid
        },
        scope = viewModelScope
    )

    val profilePhotoField = ValidatableField<Uri?, Unit>(
        initialValue = null,
        validation = { ValidationResult.Valid },
        scope = viewModelScope
    )

    val driversLicenseField = ValidatableField(
        initialValue = "",
        validation = {
            return@ValidatableField if (it.isNotEmpty() && it.length == 10 && it.all { x -> x.isDigit() }) {
                ValidationResult.Valid
            } else {
                ValidationResult.Invalid(Unit)
            }
        },
        scope = viewModelScope
    )

    val driversLicenseIssueDateField = ValidatableField<LocalDate?, Unit>(
        initialValue = null,
        validation = {
            return@ValidatableField if (it != null)
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val driversLicensePhotoField = ValidatableField<Uri?, Unit>(
        initialValue = null,
        validation = {
            return@ValidatableField if (it != null)
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    val passportPhotoField = ValidatableField<Uri?, Unit>(
        initialValue = null,
        validation = {
            return@ValidatableField if (it != null)
                ValidationResult.Valid
            else
                ValidationResult.Invalid(Unit)
        },
        scope = viewModelScope
    )

    fun continueToNextStage() {
        viewModelScope.launch {
            if (!validateStage(_currentStage.value))
                return@launch

            if (_currentStage.value == STAGE_LOGIN_INFO) {
                // TODO: Send registration request, set email error if already exists
            }

            if (_currentStage.value == STAGE_LICENSE_AND_PHOTOS) {
                // TODO: Send info registration requests, set label text above 'next' button to the error message
                _navigationEvent.send(NavigationEvent.SignUpSuccessful)
                return@launch
            }

            _currentStage.value++
        }
    }

    private suspend fun validateStage(stage: Int): Boolean {
        return when (stage) {
            STAGE_LOGIN_INFO -> {
                emailField.markStateChanged()
                passwordField.markStateChanged()
                repeatPasswordField.markStateChanged()
                acceptTermsField.markStateChanged()

                val emailFieldValidationState = emailField.actualValidationState.first()
                val passwordFieldValidationState = passwordField.actualValidationState.first()
                val repeatPasswordFieldValidationState = repeatPasswordField.actualValidationState.first()
                val acceptTermsFieldValidationState = acceptTermsField.actualValidationState.first()

                return emailFieldValidationState is ValidationResult.Valid &&
                        passwordFieldValidationState is ValidationResult.Valid &&
                        repeatPasswordFieldValidationState is ValidationResult.Valid &&
                        acceptTermsFieldValidationState is ValidationResult.Valid
            }

            STAGE_PERSONAL_DETAILS -> {
                surnameField.markStateChanged()
                nameField.markStateChanged()
                patronymicField.markStateChanged()
                birthdayField.markStateChanged()
                sexField.markStateChanged()

                val surnameFieldValidationState = surnameField.actualValidationState.first()
                val nameFieldValidationState = nameField.actualValidationState.first()
                val patronymicFieldValidationState = patronymicField.actualValidationState.first()
                val birthdayFieldValidationState = birthdayField.actualValidationState.first()
                val sexFieldValidationState = sexField.actualValidationState.first()

                return surnameFieldValidationState is ValidationResult.Valid &&
                        nameFieldValidationState is ValidationResult.Valid &&
                        patronymicFieldValidationState is ValidationResult.Valid &&
                        birthdayFieldValidationState is ValidationResult.Valid &&
                        sexFieldValidationState is ValidationResult.Valid
            }

            STAGE_LICENSE_AND_PHOTOS -> {
                profilePhotoField.markStateChanged()
                driversLicenseField.markStateChanged()
                driversLicenseIssueDateField.markStateChanged()
                driversLicensePhotoField.markStateChanged()
                passportPhotoField.markStateChanged()

                val profilePhotoFieldValidationState = profilePhotoField.actualValidationState.first()
                val driversLicenseFieldValidationState = driversLicenseField.actualValidationState.first()
                val driversLicenseIssueDateFieldValidationState = driversLicenseIssueDateField.actualValidationState.first()
                val driversLicensePhotoFieldValidationState = driversLicensePhotoField.actualValidationState.first()
                val passportPhotoFieldValidationState = passportPhotoField.actualValidationState.first()

                return profilePhotoFieldValidationState is ValidationResult.Valid &&
                        driversLicenseFieldValidationState is ValidationResult.Valid &&
                        driversLicenseIssueDateFieldValidationState is ValidationResult.Valid &&
                        driversLicensePhotoFieldValidationState is ValidationResult.Valid &&
                        passportPhotoFieldValidationState is ValidationResult.Valid
            }

            else -> false
        }
    }

    fun returnToPreviousStage() {
        viewModelScope.launch {
            if (_currentStage.value == STAGE_LOGIN_INFO) {
                _navigationEvent.send(NavigationEvent.ReturnBack)
                return@launch
            }

            _currentStage.value--
        }
    }

    sealed class NavigationEvent {
        data object SignUpSuccessful : NavigationEvent()
        data object ReturnBack : NavigationEvent()
    }
}
