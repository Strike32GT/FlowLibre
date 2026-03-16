package com.mas.flowlibre.data.repository

import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.ArtistProfileDto

interface ArtistProfileRepository {
    suspend fun getArtistProfile(artistId: Int): ArtistProfileDto
}

class ArtistProfileRepositoryImpl : ArtistProfileRepository {
    override suspend fun getArtistProfile(artistId: Int): ArtistProfileDto {
        return try {
            val response = RetrofitClient.api.getArtistDetail(artistId)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response")
            } else {
                throw Exception("Error ${response.code()}")
            }
        }catch (e: Exception) {
            println("Error loading artist profile: ${e.message}")
            throw e
        }
    }
}