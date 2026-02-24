package com.mas.flowlibre.presentation.viewModel

sealed class SongInPlaylistState {
    object Idle : SongInPlaylistState()
    object Loading : SongInPlaylistState()
    data class Success(val exists: Boolean) : SongInPlaylistState()
    data class Error(val message: String) : SongInPlaylistState()
}