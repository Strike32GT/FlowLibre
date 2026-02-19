package com.mas.flowlibre.presentation.viewModel

import com.mas.flowlibre.data.model.Playlist


sealed class CreatePlaylistState{
    object Loading: CreatePlaylistState()
    data class Success(val playlist: Playlist) : CreatePlaylistState()
    data class Error(val message: String) : CreatePlaylistState()
}