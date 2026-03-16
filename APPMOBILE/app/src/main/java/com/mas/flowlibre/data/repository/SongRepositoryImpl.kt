package com.mas.flowlibre.data.repository

import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.Playlist
import com.mas.flowlibre.data.model.SongDTO
import com.mas.flowlibre.data.model.UserStatsDto
import com.mas.flowlibre.domain.repository.SongRepository

class SongRepositoryImpl : SongRepository {
    override suspend fun getSongs(): List<SongDTO> {
        return RetrofitClient.api.getSongs()
    }


    override suspend fun getUserPlaylists(): List<Playlist> {
        return try {
            val response = RetrofitClient.api.getUserPlaylists()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println("Error cargando playlist ${e.message}")
            emptyList()
        }
    }


    override suspend fun getUserStats(): UserStatsDto {
        return try {
            val response = RetrofitClient.api.getUserStats()
            if (response.isSuccessful) {
                response.body() ?: UserStatsDto(0,0,0)
            } else {
                UserStatsDto(0,0,0)
            }
        }catch (e: Exception) {
            println("Error al cargar estadistica ${e.message}")
            UserStatsDto(0,0,0)
        }
    }
}