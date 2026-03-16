package com.mas.flowlibre.presentation.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.data.model.AddSongToPlaylistRequest
import com.mas.flowlibre.data.model.UserStatsDto
import com.mas.flowlibre.domain.model.PlayList
import com.mas.flowlibre.data.repository.SongRepositoryImpl
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.domain.repository.SongRepository
import com.mas.flowlibre.presentation.services.MediaSessionService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying : StateFlow<Boolean> = _isPlaying.asStateFlow()
    private val _userPlayLists = MutableStateFlow<List<PlayList>>(emptyList())
    val userPlayLists: StateFlow<List<PlayList>> = _userPlayLists
    private val _showAddToPlaylistDialog = MutableStateFlow(false)
    val showAddToPlaylistDialog : StateFlow<Boolean> = _showAddToPlaylistDialog
    private val _songToAdd = MutableStateFlow<Song?>(null)
    val songToAdd : StateFlow<Song?> = _songToAdd
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message
    private var currentSongIndex = 0
    private val _isShuffleOn = MutableStateFlow(false)
    val isShuffleOn: StateFlow<Boolean> = _isShuffleOn
    private val _repeatMode = MutableStateFlow(0)
    val repeatMode : StateFlow<Int> = _repeatMode
    private val _userStats = MutableStateFlow<UserStatsDto>(UserStatsDto(0,0,0))
    val userStats : StateFlow<UserStatsDto> = _userStats
    private val _mediaCommand = MutableStateFlow<String?>(null)
    val mediaCommand : StateFlow<String?> = _mediaCommand
    private var currentContext: Context? = null


    fun setContext(context: Context) {
        currentContext = context
    }




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
                        artistName = dto.artist_name,
                        coverUrl = dto.cover_url,
                        audioUrl = dto.audio_url,
                        duration = dto.duration
                    )
                }
                _songs.value = domainSongs
            } catch (e: Exception){

            }
        }
    }


    fun playSong(context: Context, song: Song) {
        val songsList = _songs.value
        currentSongIndex = songsList.indexOfFirst { it.id ==song.id }


        exoPlayer?.release()
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            val fullAudioUrl = "http://10.0.2.2:8000" + song.audioUrl //ip del celular
            val mediaItem = MediaItem.fromUri(fullAudioUrl)
            setMediaItem(mediaItem)
            prepare()

            addListener(object: Player.Listener {
                override fun onIsPlayingChanged(isPlaying : Boolean) {
                    if (isPlaying){
                        startPositionUpdates()
                    }
                }


                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        when (_repeatMode.value) {
                            0 -> playNextSong(context)
                            1 -> playNextSong(context)
                            2 -> playSong(context, song)
                        }
                    }
                }
            })
            play()
        }
        _currentSong.value = song
        _isPlaying.value = true
        startMediaSession(context,song)
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
        _isPlaying.value = false
        currentContext?.let { context ->
            _currentSong.value?.let { song ->
                updateMediaSession(context,song,false)
            }
        }
    }

    fun resumeSong() {
        exoPlayer?.play()
        _isPlaying.value = true
        currentContext?.let { context ->
            _currentSong.value?.let { song ->
                updateMediaSession(context,song,true)
            }
        }
    }


    fun loadUserPlaylists() {
        viewModelScope.launch {
            try {
                val playlists =  songRepository.getUserPlaylists()
                val domainPlaylists = playlists.map { playlist ->
                    PlayList(
                        id = playlist.id,
                        name = playlist.name,
                        totalSongs = 0,
                        durationLabel = "0:00"
                    )
                }
                _userPlayLists.value = domainPlaylists
            } catch (e: Exception) {
                println("Error al cargando playlist")
                _userPlayLists.value = emptyList()
            }
        }
    }


    fun showAddToPlaylistDialog(song: Song){
        _songToAdd.value = song
        _showAddToPlaylistDialog.value = true
        loadUserPlaylists()
    }


    fun hideAddToPlaylistDialog(){
        _showAddToPlaylistDialog.value = false
        _songToAdd.value = null
    }


    fun addSongToPlaylist(playlistId: Int, song: Song) {
        viewModelScope.launch {
            try {
                val checkResponse = RetrofitClient.api.checkSongInPlaylist(playlistId, song.id)

                if (checkResponse.isSuccessful) {
                    val existsmap = checkResponse.body()
                    val songExits = existsmap?.get("exists") ?: false

                    if (!songExits) {
                        val addRequest = AddSongToPlaylistRequest(
                            playlist = playlistId,
                            song = song.id
                        )
                        val response = RetrofitClient.api.addSongToPlaylist(addRequest)

                        if (response.isSuccessful) {
                            _message.value = "Cancion agregada exitosamente"
                            println("Canción agregada exitosamente a la playlist")
                        } else {
                            _message.value = "Error al agregar canción"
                        }
                    } else {
                        _message.value = "La canción ya está en la playlist"
                    }
                }
            }catch (e: Exception){
                println("Error al agregar cancion: ${e.message}")
            }
        }
    }


    fun playNextSong(context: Context) {
        val songsList = _songs.value
        if (songsList.isNotEmpty()) {
            currentSongIndex = if (_isShuffleOn.value) {
                songsList.indices.random()
            } else {
                (currentSongIndex + 1) % songsList.size
            }
            val nextSong = songsList[currentSongIndex]
            playSong(context, nextSong)
        }
    }


    fun playPreviousSong(context: Context) {
        val songsList = _songs.value
        if (songsList.isNotEmpty()) {
            currentSongIndex = if (_isShuffleOn.value) {
                songsList.indices.random()
            } else {
                (currentSongIndex - 1 + songsList.size) % songsList.size
            }
            val previusSong = songsList[currentSongIndex]
            playSong(context, previusSong)
        }
    }

    fun toggleShuffle() {
        _isShuffleOn.value = !_isShuffleOn.value
    }


    fun toggleRepeat(){
        _repeatMode.value = (_repeatMode.value + 1) % 3
    }


    fun clearMessage(){
        _message.value = null
    }


    fun loadUserStats(){
        viewModelScope.launch {
            try {
                val stats = songRepository.getUserStats()
            }catch (e: Exception) {
                println("${e.message}")
                _userStats.value = UserStatsDto(0,0,0)
            }
        }
    }


    fun processMediaCommand(command: String){
        println("🎵 ===== HomeViewModel: processMediaCommand =====")
        println("🎵 HomeViewModel: Comando recibido = '$command'")
        println("🎵 HomeViewModel: isPlaying actual = ${isPlaying.value}")
        println("🎵 HomeViewModel: exoPlayer = $exoPlayer")


        when (command) {
            "play" -> {
                println("🎵 HomeViewModel: Ejecutando resumeSong()")
                resumeSong()
            }

            "pause" -> {
                println("🎵 HomeViewModel: Ejecutando pauseSong()")
                pauseSong()
            }
            "play_pause" -> {
                println("🎵 HomeViewModel: Ejecutando play_pause")
                if (isPlaying.value) {
                    println("🎵 HomeViewModel: Pausando canción")
                    pauseSong() 
                } else {
                    println("🎵 HomeViewModel: Reanudando canción")
                    resumeSong()
                }
            }
            "next" -> {
                println("🎵 HomeViewModel: Ejecutando next")
                currentContext?.let { context ->
                    playNextSong(context)
                }
            }
            "previous" -> {
                println("🎵 HomeViewModel: Ejecutando previous")
                currentContext?.let { context ->
                    playPreviousSong(context)
                }
            }
        }
        println("🎵 HomeViewModel: isPlaying después = ${isPlaying.value}")
        println("🎵 ===== HomeViewModel: terminado =====")
    }


    fun startMediaSession(context: Context, song: Song) {
        try {
            val intent = Intent(context, MediaSessionService::class.java)
            context.startService(intent)

            val mediaServiceIntent = Intent(context, MediaSessionService::class.java).apply {
                putExtra("song_id",song.id)
                putExtra("song_title", song.title)
                putExtra("song_artist", song.artistName)
                putExtra("song_cover_url", song.coverUrl)
                putExtra("isPlaying", true)
            }
            context.startService(mediaServiceIntent)
        }catch (e: Exception) {
            println("Error starting media session: ${e.message}")
        }
    }


    fun updateMediaSession(context: Context, song: Song, isPlaying: Boolean){
        try {
            val intent = Intent(context, MediaSessionService::class.java).apply {
                putExtra("song_id", song.id)
                putExtra("song_title", song.title)
                putExtra("song_artist", song.artistName)
                putExtra("song_cover_url", song.coverUrl)
                putExtra("isPlaying",isPlaying)
                action = "com.mas.flowlibre.UPDATE_NOTIFICATION"
            }
            context.startService(intent)
        }catch (e: Exception) {
            println("Error updating media session: ${e.message}")
        }
    }


    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}