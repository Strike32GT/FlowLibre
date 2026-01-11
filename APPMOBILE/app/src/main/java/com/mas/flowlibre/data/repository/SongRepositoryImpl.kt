package com.mas.flowlibre.data.repository

import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.SongDTO
import com.mas.flowlibre.domain.repository.SongRepository

class SongRepositoryImpl : SongRepository {
    override suspend fun getSongs(): List<SongDTO> {
        return RetrofitClient.api.getSongs()
    }
}