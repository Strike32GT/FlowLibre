package com.mas.flowlibre.presentation.viewModel

sealed class AddSongToPlaylistState {
    object Loading : AddSongToPlaylistState()
    object Success : AddSongToPlaylistState()
    data class Error(val message: String) : AddSongToPlaylistState()
}