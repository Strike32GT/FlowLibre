package com.mas.flowlibre.domain.repository

import com.mas.flowlibre.data.model.ArtistDto

interface ArtistRepository {
    suspend fun getBuscarArtista(): List<ArtistDto>
    suspend fun getPopularArtists(): List<ArtistDto>
    suspend fun searchArtists(query: String): List<ArtistDto>
}