package com.mas.flowlibre.presentation.viewModel

import com.mas.flowlibre.data.model.UserDto

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val user: UserDto) : RegisterState()
    data class Error(val message: String) : RegisterState()
}