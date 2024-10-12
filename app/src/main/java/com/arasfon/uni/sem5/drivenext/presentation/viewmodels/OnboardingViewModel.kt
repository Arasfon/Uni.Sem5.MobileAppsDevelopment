package com.arasfon.uni.sem5.drivenext.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arasfon.uni.sem5.drivenext.domain.usecases.SetIsOnboardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setIsOnboardingCompleteUseCase: SetIsOnboardingCompleteUseCase
) : ViewModel() {
    val pageCount = 3

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _navigationEvent = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onNextClicked() {
        if (_currentPage.value < pageCount - 1) {
            _currentPage.value += 1
        } else {
            finishOnboarding()
        }
    }

    fun onSkipClicked() {
        finishOnboarding()
    }

    private fun finishOnboarding() {
        viewModelScope.launch {
            setIsOnboardingCompleteUseCase(true)
            _navigationEvent.send(NavigationEvent.FinishOnboarding)
        }
    }

    sealed class NavigationEvent {
        data object FinishOnboarding : NavigationEvent()
    }
}
