package com.mas.flowlibre.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.mas.flowlibre.data.repository.SongRepositoryImpl
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.domain.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (private val songRepository: SongRepository = SongRepositoryImpl() ): ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    private var exoPlayer: ExoPlayer? = null

    init {
        viewModelScope.launch {
            try{
                val apiDtos = songRepository.getSongs()
                val domainSongs = apiDtos.map { dto ->
                    Song(
                        id = dto.id,
                        title = dto.title,
                        artistName = "Artists ${dto.artist_id}",
                        coverUrl = dto.cover_url,
                        audioUrl = dto.audio_url
                    )
                }
                _songs.value = domainSongs
            } catch (e: Exception){

            }
        }
    }


    fun playSong(context: Context, song: Song) {
        exoPlayer?.release()
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            val fullAudioUrl = "http://ip:8000" + song.audioUrl //ip del celular
            val mediaItem = MediaItem.fromUri(fullAudioUrl)
            setMediaItem(mediaItem)
            prepare()
            play()
        }

        _currentSong.value = song
    }

    fun pauseSong(){
        exoPlayer?.pause()
    }

    fun resumeSong() {
        exoPlayer?.play()
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}