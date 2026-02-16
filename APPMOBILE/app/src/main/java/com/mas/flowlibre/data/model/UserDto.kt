package com.mas.flowlibre.data.model



data class UserDto(

    val id_user: Int,

    val username: String,

    val email: String,

    val created_at: String,

    val access_token: String,
    val refresh_token: String
)