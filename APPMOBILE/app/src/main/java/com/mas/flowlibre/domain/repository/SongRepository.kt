package com.mas.flowlibre.domain.repository

import com.mas.flowlibre.data.model.SongDTO

interface SongRepository {
    suspend fun getSongs(): List<SongDTO>
}