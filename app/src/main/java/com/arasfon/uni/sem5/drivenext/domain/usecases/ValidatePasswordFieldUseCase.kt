package com.arasfon.uni.sem5.drivenext.domain.usecases

import com.arasfon.uni.sem5.drivenext.domain.models.validation.ValidationResult
import javax.inject.Inject

class ValidatePasswordFieldUseCase @Inject constructor() {
    operator fun invoke(password: String) : ValidationResult<Unit> {
        return if (password.isNotEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(Unit)
    }
}
