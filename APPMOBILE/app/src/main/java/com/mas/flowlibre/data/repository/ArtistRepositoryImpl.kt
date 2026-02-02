package com.mas.flowlibre.data.repository



import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.ArtistDto

import com.mas.flowlibre.domain.repository.ArtistRepository





class ArtistRepositoryImpl : ArtistRepository {

    override suspend fun getBuscarArtista(): List<ArtistDto> {

        return RetrofitClient.api.searchArtists("")

    }



    override suspend fun getPopularArtists(): List<ArtistDto> {

        return RetrofitClient.api.searchArtists("")

    }



    override suspend fun searchArtists(query: String): List<ArtistDto> {

        return RetrofitClient.api.searchArtists(query)

    }

}