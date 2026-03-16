package com.mas.flowlibre.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.navigation.*
import coil3.compose.AsyncImage
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.presentation.viewModel.HomeViewModel

@Composable
fun MiniPlayer(
    song: Song,
    homeViewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currentPosition by homeViewModel.currentPosition.collectAsState()
    val duration by homeViewModel.duration.collectAsState()
    val isDragging by homeViewModel.isDragging.collectAsState()
    val isPlaying by homeViewModel.isPlaying.collectAsState()
    val userPlaylists by homeViewModel.userPlayLists.collectAsState()
    val songToAdd by homeViewModel.songToAdd.collectAsState()



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                navController.navigate("now_playing")
            },
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
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
                    }

                    IconButton(
                        onClick = {/**/}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Like",
                            tint = Color(0xFF6FE4FF),
                            modifier = Modifier.size(22.dp)
                        )
                    }


                    IconButton(
                        onClick = {
                            homeViewModel.showAddToPlaylistDialog(song)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }


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
                        } else {
                            homeViewModel.resumeSong()
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

            }
        }
    }
}


@Composable
fun AddToPlaylistDialog(
    homeViewModel: HomeViewModel,
    onDismiss: () -> Unit
) {
    val userPlaylists by homeViewModel.userPlayLists.collectAsState()
    val songToAdd by homeViewModel.songToAdd.collectAsState()


    if (homeViewModel.showAddToPlaylistDialog.collectAsState().value && songToAdd != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Agregar a Playlist",
                    color = Color.White
                )
            },
            text = {
                Column{
                    Text(
                        text = "Selecciona una playlist para agregar",
                        color = Color(0xFFA9A9B2),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    userPlaylists.forEach { playList ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    homeViewModel.addSongToPlaylist(playList.id, songToAdd!!)
                                    onDismiss()
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlaylistAdd,
                                contentDescription = null,
                                tint = Color(0xFF6FE4FF),
                                modifier = Modifier.size(24.dp)
                            )


                            Spacer(modifier = Modifier.width(12.dp))


                            Text(
                                text = playList.name,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar", color = Color(0xFF6FE4FF))
                }
            },
            containerColor = Color(0xFF1E1E24)
        )
    }
}