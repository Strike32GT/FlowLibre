package com.mas.flowlibre.presentation.viewModel

import com.mas.flowlibre.data.model.UserDto

sealed class LoginState{
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: UserDto) : LoginState()
    data class Error(val message: String) : LoginState()
}