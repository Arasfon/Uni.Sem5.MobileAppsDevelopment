package com.arasfon.uni.sem5.drivenext.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arasfon.uni.sem5.drivenext.domain.usecases.GetIsOnboardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getIsOnboardingCompleteUseCase: GetIsOnboardingCompleteUseCase
) : ViewModel() {
    private val _isOnboardingComplete = MutableStateFlow(false)
    val isOnboardingComplete: StateFlow<Boolean> = _isOnboardingComplete.asStateFlow()

    init {
        viewModelScope.launch {
            getIsOnboardingCompleteUseCase().collect { isOnboardingComplete ->
                _isOnboardingComplete.value = isOnboardingComplete
            }
        }
    }
}
