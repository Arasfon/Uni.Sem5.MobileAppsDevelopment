package com.arasfon.uni.sem5.drivenext.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arasfon.uni.sem5.drivenext.domain.usecases.SetIsOnboardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setIsOnboardingCompleteUseCase: SetIsOnboardingCompleteUseCase
) : ViewModel() {
    val pageCount = 3

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onNextClicked() {
        if (_currentPage.value < pageCount - 1) {
            _currentPage.value += 1
        } else {
            completeOnboarding()
        }
    }

    fun onSkipClicked() {
        completeOnboarding()
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            setIsOnboardingCompleteUseCase(true)
            _navigationEvent.emit(NavigationEvent.FinishOnboarding)
        }
    }

    sealed class NavigationEvent {
        data object FinishOnboarding : NavigationEvent()
    }
}
