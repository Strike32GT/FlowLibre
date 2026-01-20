package com.mas.flowlibre.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.mas.flowlibre.data.repository.SongRepositoryImpl
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.domain.repository.SongRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (private val songRepository: SongRepository = SongRepositoryImpl() ): ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    private var exoPlayer: ExoPlayer? = null


    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition : StateFlow<Long> = _currentPosition

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration


    private val _isDragging = MutableStateFlow(false)
    val isDragging : StateFlow<Boolean> = _isDragging


    fun formatTime(ms: Long):String {
        val seconds = (ms/1000) % 60
        val minutes = (ms/1000/60) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun updatePosition() {
        exoPlayer?.let {  player ->
            if (player.duration > 0){
                _currentPosition.value = player.currentPosition
                _duration.value = player.duration
            }
        }
    }

    fun searchPosition(position: Long){
        exoPlayer?.seekTo(position)
        _currentPosition.value = position
    }


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

            addListener(object: Player.Listener {
                override fun onIsPlayingChanged(isPlaying : Boolean) {
                    if (isPlaying){
                        startPositionUpdates()
                    }
                }
            })
            play()
        }
        _currentSong.value = song
    }


    private fun startPositionUpdates() {
        viewModelScope.launch {
            delay(2000)
            while (exoPlayer?.isPlaying == true) {
                delay(1000)
                exoPlayer?.let { player ->
                    val pos = player.currentPosition
                    val dur = player.duration
                    println("DEBUG: Pos=$pos, Dur=$dur, Playing=${player.isPlaying}")
                    _currentPosition.value = pos
                    _duration.value = dur
                }
            }
        }
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