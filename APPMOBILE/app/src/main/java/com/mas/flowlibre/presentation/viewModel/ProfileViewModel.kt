package com.mas.flowlibre.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mas.flowlibre.data.model.UserDto
import com.mas.flowlibre.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileViewModel(private val context: Context) : ViewModel() {

    private val sessionManager = SessionManager(context)

    private val _currentUser = MutableStateFlow<UserDto?>(null)
    val currentUser: StateFlow<UserDto?> = _currentUser

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val user = sessionManager.getUser()
            _currentUser.value = user
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _currentUser.value = null
        }
    }
}