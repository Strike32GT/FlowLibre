package com.mas.flowlibre.data.model

data class SongDTO(
    val id: Int,
    val title: String,
    val duration: Int,
    val audio_url: String,
    val cover_url: String,
    val artist_id: Int,
    val artist_name: String,
    val album_id: Int?,
    val created_at: String?
)