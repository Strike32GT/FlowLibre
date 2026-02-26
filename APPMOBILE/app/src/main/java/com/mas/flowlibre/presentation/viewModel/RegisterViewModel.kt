package com.mas.flowlibre.presentation.viewModel

import android.content.Context
import androidx.lifecycle.*
import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.RegisterRequest
import com.mas.flowlibre.data.session.SessionManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class RegisterViewModel(private val context: Context) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterState?>(null)
    val registerState : StateFlow<RegisterState?> = _registerState
    private val sessionManager = SessionManager(context)


    fun register(username: String, email:String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                val response = RetrofitClient.api.register(RegisterRequest(username, email, password))
                if (response.isSuccessful) {
                    val user = response.body()?.user
                    if (user != null) {
                        sessionManager.saveUser(user)
                        _registerState.value = RegisterState.Success(user)
                    } else{
                        _registerState.value = RegisterState.Error("Respuesta vacia")
                    }
                } else {
                    _registerState.value = RegisterState.Error("Error al crear cuenta")
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Error de conexion ${e.message}")
            }
        }
    }


    fun validatePasswords(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && password.length >=6
    }
}