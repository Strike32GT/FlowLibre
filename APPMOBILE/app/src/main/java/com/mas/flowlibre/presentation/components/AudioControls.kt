package com.mas.flowlibre.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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