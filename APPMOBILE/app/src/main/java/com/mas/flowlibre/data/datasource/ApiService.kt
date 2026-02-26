package com.mas.flowlibre.data.datasource

import com.mas.flowlibre.data.model.*
import retrofit2.Response
import retrofit2.http.*


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


    @GET("api/artists/search/")
    suspend fun searchArtists(@Query("q") query: String): List<ArtistDto>


    @GET("api/artists/{artist_id}/")
    suspend fun getArtistDetail(@Path("artist_id") artistId: Int): ArtistProfileDto


    @POST("api/users/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>


    @POST("api/users/register/")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>


    @POST("api/users/add-to-library")
    suspend fun addToLibrary(@Body request: AddToLibraryRequest): Response<AddToLibraryResponse>


    @POST("api/users/playlists/")
    suspend fun createPlaylist(@Body request: PlaylistRequest): Response<Playlist>


    @GET("api/users/playlists/my/")
    suspend fun getUserPlaylists(): Response<List<Playlist>>


    @POST("api/users/playlists/add-song/")
    suspend fun addSongToPlaylist(@Body request: AddSongToPlaylistRequest): Response<Unit>


    @GET("api/users/playlists/{playlist_id}/songs/")
    suspend fun getPlaylistSongs(@Path("playlist_id") playlistId: Int): Response<List<SongDTO>>


    @GET("api/users/playlists/{playlist_id}/songs/{song_id}/exists")
    suspend fun checkSongInPlaylist(
        @Path("playlist_id") playlistId: Int,
        @Path("song_id") songId: Int
    ): Response<Map<String, Boolean>>
}