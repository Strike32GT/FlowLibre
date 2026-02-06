package com.mas.flowlibre.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.LoginRequest
import com.mas.flowlibre.data.model.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState?>(null)
    val loginState: StateFlow<LoginState?> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = RetrofitClient.api.login(LoginRequest(email,password))
                if (response.isSuccessful){
                    _loginState.value = LoginState.Success(response.body()?.user ?: UserDto(0, "", "", ""))
                }else{
                    _loginState.value = LoginState.Error("Credenciales inválidas")
                }
            } catch (e: Exception){
                _loginState.value = LoginState.Error("Error de conexión")
            }
        }
    }
}