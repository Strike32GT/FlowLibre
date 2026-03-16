package com.mas.flowlibre.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mas.flowlibre.presentation.services.MediaSessionService
import android.view.KeyEvent
class MediaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        println("🎵 ===== MediaReceiver: onReceive llamado! =====")
        println("🎵 MediaReceiver: Action = ${intent.action}")
        println("🎵 MediaReceiver: Extras = ${intent.extras?.keySet()}")
        try {
            when (intent.action) {
                MediaSessionService.ACTION_PLAY_PAUSE -> {
                    println("🎵 MediaReceiver: ACTION_PLAY_PAUSE recibido")
                    val controlIntent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                        putExtra("command","play_pause")
                    }
                    println("🎵 MediaReceiver: Enviando broadcast play_pause")
                    context.sendBroadcast(controlIntent)
                    println("🎵 MediaReceiver: Broadcast enviado!")
                }

                MediaSessionService.ACTION_NEXT -> {
                    println("🎵 MediaReceiver: ES NEXT!")
                    val controlIntent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                        putExtra("command","next")
                    }
                    context.sendBroadcast(controlIntent)
                }

                MediaSessionService.ACTION_PREVIOUS -> {
                    println("🎵 MediaReceiver: ES PREVIOUS!")
                    val controlIntent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                        putExtra("command", "previous")
                    }
                    context.sendBroadcast(controlIntent)
                }

                Intent.ACTION_MEDIA_BUTTON -> {
                    val keyEvent = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
                    keyEvent?.let { event ->
                        when (event.action) {
                            KeyEvent.ACTION_DOWN -> {
                                when (event.keyCode) {
                                    KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                                        val controlIntent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                                            putExtra("command", "play_pause")
                                        }
                                        context.sendBroadcast(controlIntent)
                                    }

                                    KeyEvent.KEYCODE_MEDIA_NEXT -> {
                                        val controlIntent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                                            putExtra("command","next")
                                        }
                                        context.sendBroadcast(controlIntent)
                                    }

                                    KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                                        val controlIntent = Intent("com.mas.flowlibre.MEDIA_CONTROL").apply {
                                            putExtra("command","previous")
                                        }
                                        context.sendBroadcast(controlIntent)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MediaReceiver", "Error processing media control", e)
        }
    }
}