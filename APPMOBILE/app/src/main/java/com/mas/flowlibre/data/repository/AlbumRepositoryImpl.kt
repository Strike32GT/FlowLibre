package com.mas.flowlibre.data.repository

import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.AlbumDto
import com.mas.flowlibre.domain.repository.AlbumRepository

class AlbumRepositoryImpl : AlbumRepository {
    override suspend fun getAlbums(): List<AlbumDto> {
        return RetrofitClient.api.getAlbums()
    }
}