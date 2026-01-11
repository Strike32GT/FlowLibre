package com.mas.flowlibre.data.model

data class PlaylistDto(
    val id_playlist: Int,
    val name: String,
    val description: String?,
    val is_public: Boolean,
    val total_songs: Int,
    val owner: UserDto
)