package com.arasfon.uni.sem5.drivenext.presentation.util

/**
 * Validation state, [Invalid] state of which can have an error of type [T]
 */
sealed class ValidationState<out T> {
    data object Valid : ValidationState<Nothing>()
    data class Invalid<out T>(val error: T) : ValidationState<T>()
}
