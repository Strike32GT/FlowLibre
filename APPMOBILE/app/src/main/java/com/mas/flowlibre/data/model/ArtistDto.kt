package com.mas.flowlibre.data.model

data class ArtistDto(
    val id_artist: Int,
    val name: String,
    val verified: Boolean,
    val description: String,
    val followers: Long,
    val awards: List<String>,
    val profile_image_url: String,
    val upcoming_concerts: List<ConcertDto>
)