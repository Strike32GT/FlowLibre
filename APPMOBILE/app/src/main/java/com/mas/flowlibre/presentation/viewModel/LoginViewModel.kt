package com.mas.flowlibre.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.LoginRequest
import com.mas.flowlibre.data.model.UserDto
import com.mas.flowlibre.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState?>(null)
    val loginState: StateFlow<LoginState?> = _loginState


    private val sessionManager = SessionManager(context)
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = RetrofitClient.api.login(LoginRequest(email,password))
                if (response.isSuccessful){
                    val user = response.body()?.user
                    if (user !=null) {
                        sessionManager.saveUser(user)
                        _loginState.value = LoginState.Success(response.body()?.user ?: UserDto(0, "", "", ""))
                    }
                }else{
                    _loginState.value = LoginState.Error("Credenciales inválidas")
                }
            } catch (e: Exception){
                _loginState.value = LoginState.Error("Error de conexión")
            }
        }
    }


    fun getCurrentUser() = sessionManager.getUser()

    fun isLoggedIn() =sessionManager.isLoggedIn()

    fun logout() {
        sessionManager.clearSession()
        _loginState.value = null
    }
}