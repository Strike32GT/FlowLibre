package com.mas.flowlibre.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.mas.flowlibre.domain.model.Song

@Composable
fun AudioControls(
    currentSong: Song?,
    onPlay: () -> Unit,
    onPause: () -> Unit
) {
    currentSong?.let { song ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Reproduciendo: ${song.title}",
                    modifier = Modifier.weight(1f)
                )


                Button(onClick = onPause) {
                    Text(text = "Pausar")
                }


                Spacer(modifier = Modifier.width(8.dp))


                Button(onClick = onPlay) {
                    Text("Play")
                }
            }
        }
    }
}