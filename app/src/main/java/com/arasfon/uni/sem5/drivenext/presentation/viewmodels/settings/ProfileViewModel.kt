package com.arasfon.uni.sem5.drivenext.presentation.viewmodels.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient
) : ViewModel(){
    fun getUserEmail(): String? {
        return supabaseClient.auth.currentUserOrNull()?.email
    }

    fun getUserFullName(): String? {
        return supabaseClient.auth.currentUserOrNull()?.userMetadata?.get("full_name")?.jsonPrimitive?.content
    }

    fun getUserCreationDate(): Instant? {
        return supabaseClient.auth.currentUserOrNull()?.createdAt
    }

    fun getUserGoogleEmail(): String? {
        return supabaseClient.auth.currentUserOrNull()
            ?.identities?.firstOrNull { x -> x.provider == "google" }
            ?.identityData?.get("email")?.jsonPrimitive?.content
    }

    fun signOut() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                supabaseClient.auth.signOut()
            }
        }
    }
}
