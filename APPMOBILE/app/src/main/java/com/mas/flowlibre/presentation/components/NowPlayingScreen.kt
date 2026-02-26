package com.mas.flowlibre.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.mas.flowlibre.presentation.viewModel.HomeViewModel

@Composable
fun NowPlayingScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val currentSong by homeViewModel.currentSong.collectAsState()
    val currentPosition by homeViewModel.currentPosition.collectAsState()
    val duration by homeViewModel.duration.collectAsState()
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(currentSong) {
        isPlaying = currentSong != null
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
    ){
        IconButton(
            onClick = {navController.popBackStack()},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = Color.White
            )
        }



        if (currentSong != null) {
            val song = currentSong !!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = "http://10.0.2.2:8000${song.coverUrl}",
                    contentDescription = song.title,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(20.dp))
                )

                Spacer(modifier = Modifier.height(32.dp))


                Text(
                    text = song.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = song.artistName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )


                Spacer(modifier = Modifier.height(32.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        value = if (duration > 0) currentPosition.toFloat() / duration else 0f,
                        onValueChange = { progress ->
                            val newPosition = (progress * duration).toLong()
                            homeViewModel.searchPosition(newPosition)
                        },


                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color(0xFF6FE4FF),
                            inactiveTrackColor = Color.Gray
                        ),

                        modifier = Modifier.weight(1f)
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = homeViewModel.formatTime(currentPosition),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )


                    Text(
                        text = homeViewModel.formatTime(duration),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }


                Spacer(modifier = Modifier.height(32.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {/**/}) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Anterior",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }


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
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    }


                    IconButton(
                        onClick = {/**/}
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Siguiente",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}