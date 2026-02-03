package com.mas.flowlibre.data.repository

import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.ArtistProfileDto

interface ArtistProfileRepository {
    suspend fun getArtistProfile(artistId: Int): ArtistProfileDto
}

class ArtistProfileRepositoryImpl : ArtistProfileRepository {
    override suspend fun getArtistProfile(artistId: Int): ArtistProfileDto {
        return RetrofitClient.api.getArtistDetail(artistId)
    }
}