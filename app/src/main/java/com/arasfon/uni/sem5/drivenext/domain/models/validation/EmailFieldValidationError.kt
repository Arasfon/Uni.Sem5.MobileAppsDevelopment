package com.arasfon.uni.sem5.drivenext.domain.models.validation

sealed class EmailFieldValidationError {
    data object InvalidEmail : EmailFieldValidationError()
    data object WrongCredentials : EmailFieldValidationError()
}
