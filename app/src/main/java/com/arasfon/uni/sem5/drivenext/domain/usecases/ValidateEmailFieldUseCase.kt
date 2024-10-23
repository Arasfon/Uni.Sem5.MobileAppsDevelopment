package com.arasfon.uni.sem5.drivenext.domain.usecases

import com.arasfon.uni.sem5.drivenext.domain.models.validation.ValidationResult
import com.arasfon.uni.sem5.drivenext.domain.models.validation.auth.EmailFieldValidationError
import javax.inject.Inject

class ValidateEmailFieldUseCase @Inject constructor() {
    companion object {
        private val emailRegex: Regex = Regex("""(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""")
    }

    operator fun invoke(email: String) : ValidationResult<EmailFieldValidationError> {
        return if (email.isNotEmpty() && emailRegex.matches(email))
            ValidationResult.Valid
        else
            ValidationResult.Invalid(EmailFieldValidationError.InvalidEmail)
    }
}
