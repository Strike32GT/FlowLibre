package com.mas.flowlibre.domain.repository

import com.mas.flowlibre.data.model.Playlist
import com.mas.flowlibre.data.model.SongDTO
import com.mas.flowlibre.data.model.UserStatsDto

interface SongRepository {
    suspend fun getSongs(): List<SongDTO>
    suspend fun getUserPlaylists(): List<Playlist>
    suspend fun getUserStats(): UserStatsDto
}