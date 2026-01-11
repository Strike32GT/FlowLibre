package com.mas.flowlibre.data.datasource

import com.mas.flowlibre.data.model.AlbumDto
import com.mas.flowlibre.data.model.SongDTO
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("api/songs/")
    suspend fun getSongs(): List<SongDTO>

    @GET("api/songs/{songId}/")
    suspend fun getSongDetail(@Path("songId") songId: Int): SongDTO

    @GET("api/songs/album/{albumId}/")
    suspend fun getSongsByAlbum(@Path("albumId") albumId: Int): List<SongDTO>

    @GET("api/albums/")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("api/albums/{albumId}/")
    suspend fun getAlbumDetail(@Path("albumId") albumId: Int): AlbumDto
}