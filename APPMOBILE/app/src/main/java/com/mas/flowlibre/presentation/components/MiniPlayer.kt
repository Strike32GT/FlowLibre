package com.mas.flowlibre.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil3.compose.AsyncImage
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.presentation.viewModel.HomeViewModel

@Composable
fun MiniPlayer(
    song: Song,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val currentPosition by homeViewModel.currentPosition.collectAsState()
    val duration by homeViewModel.duration.collectAsState()
    val isDragging by homeViewModel.isDragging.collectAsState()
    var isPlaying by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = "http://10.0.2.2:8000${song.coverUrl}",
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = song.title,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = song.artistName,
                    color = Color(0xFFA9A9B2),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )


                LinearProgressIndicator(
                    progress = {  if(duration > 0)  currentPosition.toFloat() / duration else 0f},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    color = Color(0xFF6FE4FF),
                    trackColor = Color(0xFF3A3A42)
                )


                Text(
                    text = "${homeViewModel.formatTime(currentPosition)} / ${homeViewModel.formatTime(duration)}",
                    color = Color(0xFFA9A9B2),
                    style = MaterialTheme.typography.bodySmall
                )


                IconButton(
                    onClick = {
                        if (isPlaying) {
                            homeViewModel.pauseSong()
                            isPlaying = false
                        } else {
                            homeViewModel.resumeSong()
                            isPlaying = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isPlaying)Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color(0xFF6FE4FF),
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(
                    onClick = {/**/}
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar a playlist",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}