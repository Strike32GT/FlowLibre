package com.mas.flowlibre.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mas.flowlibre.data.repository.SongRepositoryImpl
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.domain.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (private val songRepository: SongRepository = SongRepositoryImpl() ): ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    init {
        viewModelScope.launch {
            try{
                val apiDtos = songRepository.getSongs()
                val domainSongs = apiDtos.map { dto ->
                    Song(
                        id = dto.id,
                        title = dto.title,
                        artistName = "",
                        coverUrl = dto.cover_url
                    )
                }
                _songs.value = domainSongs
            } catch (e: Exception){
                //
            }
        }
    }
}