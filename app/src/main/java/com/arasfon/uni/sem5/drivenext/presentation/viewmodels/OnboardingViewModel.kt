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
    companion object {
        const val PAGE_COUNT = 3
    }

    private val _navigationEvent = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    fun navigateToNextPage() {
        if (_currentPage.value < PAGE_COUNT - 1) {
            _currentPage.value += 1
        } else {
            finishOnboarding()
        }
    }

    fun skipOnboarding() {
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
