package com.mas.flowlibre.domain.model

data class PlayList(
    val id: Int,
    val name: String,
    val totalSongs: Int,
    val durationLabel: String
)