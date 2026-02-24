package com.mas.flowlibre.domain.model

data class Song(
    val id: Int,
    val title: String,
    val artistName: String,
    val coverUrl: String,
    val audioUrl: String,
    val duration: Int
)