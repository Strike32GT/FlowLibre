package com.mas.flowlibre.data.repository

import com.mas.flowlibre.data.model.ArtistDto
import com.mas.flowlibre.domain.repository.ArtistRepository


class ArtistRepositoryImpl : ArtistRepository {
    override suspend fun getBuscarArtista(): List<ArtistDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularArtists(): List<ArtistDto> {
        TODO("Not yet implemented")
    }

    override suspend fun searchArtists(query: String): List<ArtistDto> {
        TODO("Not yet implemented")
    }
}