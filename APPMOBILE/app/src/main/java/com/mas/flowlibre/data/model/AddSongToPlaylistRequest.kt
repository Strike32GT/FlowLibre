package com.mas.flowlibre.data.model

data class AddSongToPlaylistRequest(
    val playlist: Int,
    val song: Int
)