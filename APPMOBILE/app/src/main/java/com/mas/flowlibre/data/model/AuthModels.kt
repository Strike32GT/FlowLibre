package com.mas.flowlibre.data.model


data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val user: UserDto, val access: String, val refresh: String, val message: String)