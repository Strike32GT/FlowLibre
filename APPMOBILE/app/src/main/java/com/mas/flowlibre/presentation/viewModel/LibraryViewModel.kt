package com.mas.flowlibre.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mas.flowlibre.domain.model.PlayList
import com.mas.flowlibre.domain.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LibraryViewModel : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs : StateFlow<List<Song>> = _songs

    private val _favoriteSongs = MutableStateFlow<List<Song>>(emptyList())
    val favoriteSongs: StateFlow<List<Song>> = _favoriteSongs

    private val _playlists = MutableStateFlow<List<PlayList>>(emptyList())
    val playlists: StateFlow<List<PlayList>> = _playlists

    private val _downloadedSongs = MutableStateFlow<List<Song>>(emptyList())
    val downloadedSongs: StateFlow<List<Song>> = _downloadedSongs

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
            _isLoading.value = true
            try {
                //
                _playlists.value = emptyList()
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
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
}