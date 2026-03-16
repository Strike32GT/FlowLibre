package com.mas.flowlibre.presentation.services

import android.app.*
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.flagging.Flags
import androidx.media.MediaBrowserServiceCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media.session.MediaButtonReceiver
import coil3.request.SuccessResult
import coil3.ImageLoader
import coil3.executeBlocking
import coil3.request.ImageRequest
import com.mas.flowlibre.MainActivity
import com.mas.flowlibre.R
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.presentation.receivers.MediaReceiver
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL


class MediaSessionService : MediaBrowserServiceCompat() {
    private lateinit var mediaSession : MediaSessionCompat
    private lateinit var notificacionManager : NotificationManager
    private lateinit var imageLoader : ImageLoader


    companion object {
        const val NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "music_playback_channel"
        const val ACTION_PLAY_PAUSE = "com.mas.flowlibre.PLAY_PAUSE"
        const val ACTION_NEXT = "com.mas.flowlibre.NEXT"
        const val ACTION_PREVIOUS = "com.mas.flowlibre.PREVIOUS"
    }


    override fun onCreate(){
        super.onCreate()
        imageLoader = ImageLoader.Builder(this).build()

        mediaSession = MediaSessionCompat(this, "FlowLibreMusicService").apply {
            setCallback(MediaSessionCallback())
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )
            isActive = true
        }
        sessionToken = mediaSession.sessionToken

        notificacionManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Controls for FlowLibre music playback"
                setShowBadge(false)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            notificacionManager.createNotificationChannel(channel)
        }
    }


    fun updateNotification(song: Song, isPlaying: Boolean){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val albumArt = loadAlbumArt(song.coverUrl)


                val metadata = MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, song.artistName)
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.title)
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "FlowLibre")
                    .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt)
                    .build()
                mediaSession.setMetadata(metadata)

                val state = PlaybackStateCompat.Builder()
                    .setState(
                        if (isPlaying) PlaybackStateCompat.STATE_PLAYING
                        else PlaybackStateCompat.STATE_PAUSED,
                        0, 1f
                    )
                    .setActions(
                       PlaybackStateCompat.ACTION_PLAY_PAUSE or
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                    )
                    .build()
                mediaSession.setPlaybackState(state)
                val notification = createNotification(song, isPlaying, albumArt)
                mediaSession.setSessionActivity(createContentIntent())
                startForeground(NOTIFICATION_ID, notification)
            } catch (e: Exception) {
                println("Error updating media session: ${e.message}")
            }
        }
    }

    private suspend fun loadAlbumArt(coverUrl: String) : Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val request = ImageRequest.Builder(this@MediaSessionService)
                    .data("http://10.0.2.2:8000$coverUrl")
                    .build()
                val result = imageLoader.executeBlocking(request)
                if (result is SuccessResult){
                    (result.image as? BitmapDrawable)?.bitmap
                } else{
                    null
                }
            }catch (e: Exception) {
                try {
                    val url = URL("http://10.0.2.2:8000$coverUrl")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val input = connection.inputStream
                    BitmapFactory.decodeStream(input)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    private fun createNotification(song: Song, isPlaying: Boolean, albumArt: Bitmap?): Notification {
        println("🎵 MediaSessionService: Creando notificación SIN MediaStyle")

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setStyle(
                MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0,1,2,3)
            )
            .setSmallIcon(R.drawable.ic_music_note)
            .setLargeIcon(albumArt)
            .setContentTitle(song.title)
            .setContentText(song.artistName)
            .setSubText("FlowLibre")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .addAction(createPlayPauseAction(isPlaying))
            .addAction(createSkipNextAction())
            .addAction(createSkipPreviousAction())
            .addAction(createCastAction())
            .build()
    }


    private fun createPlayPauseAction(isPlaying: Boolean): NotificationCompat.Action {
        val icon = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        val title = if (isPlaying) "Pause" else "Play"
        println("🎵 MediaSessionService: Creando PlayPauseAction (isPlaying=$isPlaying)")
        return NotificationCompat.Action.Builder(icon, title, createPendingIntent(ACTION_PLAY_PAUSE)).build()
    }

    private fun createSkipNextAction(): NotificationCompat.Action {
        return NotificationCompat.Action.Builder(R.drawable.ic_skip_next, "Next", createPendingIntent(ACTION_NEXT)).build()
    }

    private fun createSkipPreviousAction(): NotificationCompat.Action {
        return NotificationCompat.Action.Builder(R.drawable.ic_skip_previous, "Previous", createPendingIntent(ACTION_PREVIOUS)).build()
    }

    private fun createCastAction(): NotificationCompat.Action {
        return NotificationCompat.Action.Builder(
            R.drawable.ic_cast,
            "Cast",
            null
        ).build()
    }

    private fun createPendingIntent(action: String) : PendingIntent {
        println("🎵 MediaSessionService: Creando PendingIntent para action=$action")
        val intent = Intent(this, MediaReceiver::class.java).apply {
            this.action = action
        }
        println("🎵 MediaSessionService: Intent creado con action=${intent.action}")

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            action.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        println("🎵 MediaSessionService: PendingIntent creado")

        return pendingIntent
    }

    private fun createContentIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot("flowlibre_root", null)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        result.sendResult(mutableListOf())
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action) {
                "com.mas.flowlibre.UPDATE_NOTIFICATION" -> {
                    val song = Song(
                        id = it.getIntExtra("song_id", 0),
                        title = it.getStringExtra("song_title") ?: "",
                        artistName = it.getStringExtra("song_artist") ?: "",
                        coverUrl = it.getStringExtra("song_cover_url") ?: "",
                        audioUrl = "",
                        duration = 0
                    )
                    val isPlaying = it.getBooleanExtra("isPlaying", false)
                    updateNotification(song, isPlaying)
                }
                else -> {
                    val song = Song(
                        id = it.getIntExtra("song_id", 0),
                        title = it.getStringExtra("song_title") ?: "",
                        artistName = it.getStringExtra("song_artist") ?: "",
                        coverUrl = it.getStringExtra("song_cover_url") ?: "",
                        audioUrl = "",
                        duration = 0
                    )
                    val isPlaying = it.getBooleanExtra("isPlaying",false)
                    updateNotification(song,isPlaying)
                }
            }
        }
        return  START_STICKY
    }

    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onPlay() {
            val intent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                putExtra("command","play")
            }
            sendBroadcast(intent)
        }

        override fun onPause() {
            val intent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                putExtra("command","pause")
            }
            sendBroadcast(intent)
        }

        override fun onSkipToNext() {
            val intent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                putExtra("command","next")
            }
            sendBroadcast(intent)
        }

        override fun onSkipToPrevious() {
            val intent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                putExtra("command","previous")
            }
            sendBroadcast(intent)
        }
    }
}


