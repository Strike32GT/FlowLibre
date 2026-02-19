package com.mas.flowlibre.presentation.viewModel

import com.mas.flowlibre.domain.model.Song


sealed class LoadPlaylistSongsState {
    object Idle : LoadPlaylistSongsState()
    object Loading : LoadPlaylistSongsState()
    data class Success(val songs: List<Song>) : LoadPlaylistSongsState()
    data class Error(val message: String) : LoadPlaylistSongsState()
}