package com.mas.flowlibre.domain.repository

import com.mas.flowlibre.data.model.AlbumDto

interface AlbumRepository {
    suspend fun getAlbums(): List<AlbumDto>
}