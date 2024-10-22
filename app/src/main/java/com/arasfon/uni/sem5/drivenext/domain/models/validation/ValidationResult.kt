package com.arasfon.uni.sem5.drivenext.domain.models.validation

/**
 * Validation result, [Invalid] state of which can have an error of type [T]
 */
sealed class ValidationResult<out T> {
    data object Valid : ValidationResult<Nothing>()
    data class Invalid<out T>(val error: T) : ValidationResult<T>()
}
