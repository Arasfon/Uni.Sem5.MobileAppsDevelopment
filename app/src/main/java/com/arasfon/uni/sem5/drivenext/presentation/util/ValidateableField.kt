package com.arasfon.uni.sem5.drivenext.presentation.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.arasfon.uni.sem5.drivenext.domain.models.validation.ValidationResult
import com.arasfon.uni.sem5.drivenext.domain.models.validation.ValidationResult.Invalid
import com.arasfon.uni.sem5.drivenext.domain.models.validation.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

/**
 * A generic class representing a validatable field
 *
 * @param TValue The type of the value
 * @param TError The type of the validation error for invalid results
 * @param initialValue The initial value of the field
 * @param validation A function to validate the input and return a validation result
 * @param scope The CoroutineScope for managing StateFlows
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ValidatableField<TValue, TError>(
    initialValue: TValue,
    private val validation: Validator<TValue, TError>,
    private val scope: CoroutineScope
) {
    /**
     * The current value of the field.
     */
    var value by mutableStateOf(initialValue)
        private set

    private val _isInInitialState = MutableStateFlow(true)

    private val _forcedValidationError = MutableStateFlow<Invalid<TError>?>(null)

    /**
     * A [StateFlow] that emits actual validation state.
     * Automatically revalidates whenever [value] changes.
     * Does not take display validation error into account.
     */
    val actualValidationState: StateFlow<ValidationResult<TError>> =
        snapshotFlow { value }
            .mapLatest { validation(it) }
            .distinctUntilChanged()
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = validation(initialValue)
            )

    /**
     * A [StateFlow] that emits the validation state and includes any forced display
     * validation errors. Automatically revalidates whenever [value] changes
     * or when a display error is forced.
     */
    val displayValidationState: StateFlow<ValidationResult<TError>> =
        combine(
            actualValidationState,
            _forcedValidationError
        ) { validationResult, forcedError ->
            // If a forced error is present, it takes precedence
            forcedError ?: validationResult
        }
            .distinctUntilChanged()
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = validation(initialValue)
            )

    /**
     * A [StateFlow] that emits the latest validation error whenever it changes.
     * Does not emit when [actualValidationState] is [ValidationResult.Valid], so the last error
     * is persisted.
     */
    val lastDisplayError: StateFlow<TError?> =
        displayValidationState
            .filter { validationState ->
                validationState !is ValidationResult.Valid
            }
            .mapLatest { validationState ->
                val invalidValidationState = validationState as Invalid<TError>
                return@mapLatest invalidValidationState.error
            }
            .distinctUntilChanged()
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    /**
     * A [StateFlow] that emits `true` if the field is invalid and no longer in its initial state,
     * indicating when to display error messages.
     * Takes forced display error into account too.
     */
    val shouldShowError: StateFlow<Boolean> =
        combine(displayValidationState, _isInInitialState) { validationState, isInitial ->
            validationState is Invalid && !isInitial
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    /**
     * Updates the value of the field
     *
     * @param newValue The new value to set
     */
    fun updateValue(newValue: TValue) {
        value = newValue
        _isInInitialState.value = false
        clearForcedDisplayError()
    }

    /**
     * Forces display validation error until state changed via either [updateValue] or [markStateChanged],
     * or explicitly cleared using [clearForcedDisplayError]
     */
    fun forceDisplayError(error: TError) {
        _forcedValidationError.value = Invalid(error)
    }

    /**
     * Clears forced display validation error
     */
    fun clearForcedDisplayError() {
        _forcedValidationError.value = null
    }

    /**
     * Marks the field as no longer in its initial state and re-validates it.
     * Typically called when the user attempts to perform some action that requires valid field
     * but they didn't interact with the field at all.
     */
    fun markStateChanged() {
        _isInInitialState.value = false
        clearForcedDisplayError()
    }
}
