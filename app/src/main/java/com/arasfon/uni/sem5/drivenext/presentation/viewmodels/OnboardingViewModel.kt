package com.arasfon.uni.sem5.drivenext.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arasfon.uni.sem5.drivenext.domain.usecases.SetIsOnboardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setIsOnboardingCompleteUseCase: SetIsOnboardingCompleteUseCase
) : ViewModel() {
    fun completeOnboarding() {
        viewModelScope.launch {
            setIsOnboardingCompleteUseCase(true)
        }
    }
}
