package com.mas.flowlibre.data.model

data class ArtistProfileDto(
    val artist: ArtistDto,
    val songs: List<SongDTO>,
    val albums: List<AlbumDto>
)