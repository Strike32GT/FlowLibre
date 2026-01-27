package com.mas.flowlibre.domain.model

data class Artist (
    val id: Int,
    val name: String,
    val verified: Boolean,
    val description: String = "",
    val followers: Int = 0,
    val profileImageUrl: String = ""
)