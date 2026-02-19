package com.mas.flowlibre.presentation.viewModel



import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope

import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.AddSongToPlaylistRequest

import com.mas.flowlibre.data.model.AddToLibraryRequest
import com.mas.flowlibre.data.model.Playlist
import com.mas.flowlibre.data.model.PlaylistRequest
import com.mas.flowlibre.data.session.SessionManager

import com.mas.flowlibre.domain.model.PlayList

import com.mas.flowlibre.domain.model.Song

import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch





class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)

    private val _songs = MutableStateFlow<List<Song>>(emptyList())

    val songs : StateFlow<List<Song>> = _songs



    private val _favoriteSongs = MutableStateFlow<List<Song>>(emptyList())

    val favoriteSongs: StateFlow<List<Song>> = _favoriteSongs



    private val _playlists = MutableStateFlow<List<PlayList>>(emptyList())

    val playlists: StateFlow<List<PlayList>> = _playlists



    private val _downloadedSongs = MutableStateFlow<List<Song>>(emptyList())

    val downloadedSongs: StateFlow<List<Song>> = _downloadedSongs



    private val _addToLibraryState = MutableStateFlow<AddToLibraryState?>(null)

    val addToLibraryState: StateFlow<AddToLibraryState?> = _addToLibraryState


    private val _userPlaylists = MutableStateFlow<List<Playlist>>(emptyList())
    val userPlaylists : StateFlow<List<Playlist>> = _userPlaylists


    private val _createPlaylistState = MutableStateFlow<CreatePlaylistState?>(null)
    val createPlaylistState: StateFlow<CreatePlaylistState?> = _createPlaylistState

    private val _addSongToPlaylistState= MutableStateFlow<AddSongToPlaylistState?>(null)
    val addSongToPlaylistState: StateFlow<AddSongToPlaylistState?> = _addSongToPlaylistState

    private val _playlistSongs = MutableStateFlow<List<Song>>(emptyList())
    val playlistSongs: StateFlow<List<Song>> = _playlistSongs


    private val _loadPlaylistSongsState = MutableStateFlow<LoadPlaylistSongsState>(LoadPlaylistSongsState.Idle)
    val loadPlaylistSongsState: StateFlow<LoadPlaylistSongsState> = _loadPlaylistSongsState



    fun addToLibrary(songId:Int){

        viewModelScope.launch {

            _addToLibraryState.value = AddToLibraryState.Loading

            try {
                if (!sessionManager.isLoggedIn()) {
                    _addToLibraryState.value = AddToLibraryState.Loading
                    return@launch
                }
                val response = RetrofitClient.api.addToLibrary(AddToLibraryRequest(songId))

                if (response.isSuccessful) {

                    _addToLibraryState.value = AddToLibraryState.Success(response.body()?.message ?: "Agregado")

                } else {

                    _addToLibraryState.value = AddToLibraryState.Error("Error al agregar")

                }

            }catch (e: Exception) {

                _addToLibraryState.value = AddToLibraryState.Error("Error de conexión")

            }

        }

    }







    private val _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> = _isLoading



    fun loadFavoriteSongs() {

        viewModelScope.launch {

            _isLoading.value = true

            try {

                //

                _favoriteSongs.value = emptyList()

            } catch (e: Exception) {



            } finally {

                _isLoading.value = false

            }

        }

    }





    fun loadUserPlaylists() {

        viewModelScope.launch {
            try {
                if (!sessionManager.isLoggedIn()) return@launch

                val response = RetrofitClient.api.getUserPlaylists()

                if (response.isSuccessful) {
                    _userPlaylists.value = response.body() ?: emptyList()
                }
            }catch (e: Exception) {
                //
            }
        }
    }



    fun loadDownloadedSongs() {

        viewModelScope.launch {

            _isLoading.value = true

            try {

                _downloadedSongs.value = emptyList()

            } catch (e: Exception) {



            } finally {

                _isLoading.value = false

            }

        }

    }





    fun toggleFavorite(song: Song) {

        viewModelScope.launch {

            try{



            }catch (e: Exception){



            }

        }

    }

    fun createPlaylist(name: String) {
        viewModelScope.launch {
            _createPlaylistState.value = CreatePlaylistState.Loading
            try {
                if (!sessionManager.isLoggedIn()) {
                    _createPlaylistState.value = CreatePlaylistState.Error("No autenticado")
                    return@launch
                }

                val response = RetrofitClient.api.createPlaylist(PlaylistRequest(name))

                if (response.isSuccessful) {
                    _createPlaylistState.value = CreatePlaylistState.Success(response.body()!!)
                    loadUserPlaylists()
                } else {
                    _createPlaylistState.value = CreatePlaylistState.Error("Error al crear la playlist")
                }
            } catch (e: Exception) {
                _createPlaylistState.value = CreatePlaylistState.Error("Error de conexion")
            }
        }
    }



    fun addSongToPlaylist(playlistId: Int, songId: Int) {
        viewModelScope.launch {
            _addSongToPlaylistState.value = AddSongToPlaylistState.Loading
            try {
                if (!sessionManager.isLoggedIn()) {
                    _addSongToPlaylistState.value = AddSongToPlaylistState.Error("No autenticado")
                    return@launch
                }

                val response = RetrofitClient.api.addSongToPlaylist(
                    AddSongToPlaylistRequest(playlistId, songId)
                )

                if (response.isSuccessful) {
                    _addSongToPlaylistState.value = AddSongToPlaylistState.Success
                } else {
                    _addSongToPlaylistState.value = AddSongToPlaylistState.Error("Error al agregar una cancion")
                }
            }catch (e: Exception) {
                _addSongToPlaylistState.value = AddSongToPlaylistState.Error("Error de conexion")
            }
        }
    }


    fun loadPlaylistSongs(playlistId: Int) {
        viewModelScope.launch {
            _loadPlaylistSongsState.value = LoadPlaylistSongsState.Loading

            try {
                val response = RetrofitClient.api.getPlaylistSongs(playlistId)

                if (response.isSuccessful) {
                    val songs = response.body()?.map { songDTO ->
                        Song(
                            id = songDTO.id,
                            title = songDTO.title,
                            artistName = songDTO.artist_name,
                            coverUrl = songDTO.cover_url,
                            audioUrl = songDTO.audio_url
                        )
                    } ?: emptyList()

                    _playlistSongs.value = songs
                    _loadPlaylistSongsState.value = LoadPlaylistSongsState.Success(songs)
                } else {
                    _loadPlaylistSongsState.value = LoadPlaylistSongsState.Error("Error al cargar canciones")
                }
            } catch (e: Exception) {
                _loadPlaylistSongsState.value = LoadPlaylistSongsState.Error("Error de conexión")
            }
        }
    }

}