package com.mas.flowlibre.data.model

data class AlbumDto(
    val id_album: Int,
    val title: String,
    val cover_url: String,
    val release_date: String?,
    val artist_id: Int,
    val created_at: String?
)