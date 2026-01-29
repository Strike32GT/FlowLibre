package com.mas.flowlibre.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mas.flowlibre.domain.model.PlayList
import com.mas.flowlibre.presentation.navigation.BottomNavigationBarWithNavigation
import com.mas.flowlibre.presentation.viewModel.LibraryViewModel

@Composable
fun Biblioteca(
    navController: NavHostController,
    viewModel: LibraryViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(2) }
    val playlists by viewModel.playlists.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0E))
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier
                    .height(45.dp)
                    .statusBarsPadding())
            }

            item {
                Text(
                    text = "Tu Biblioteca",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }


            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "3h 25min",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )

                        Text(
                            text = "escuchadas",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFFA9A9B2)
                            )
                        )
                    }


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${playlists.size}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )

                        Text(
                            text = "playlist",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFFA9A9B2)
                            )
                        )
                    }
                }
            }

            item {
                Text(
                    text = "PlayList",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }


            if(playlists.isEmpty()){
                item{
                    EmptyPlaylistsState()
                }
            }else {
                items(playlists) { playlist ->
                    PlaylistItem(playlist = playlist)
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        BottomNavigationBarWithNavigation(
            navController = navController,
            selectedTab = selectedTab,
            onTabSelected = { tab -> selectedTab = tab },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
                .zIndex(3f)
        )
    }
}


@Composable
fun PlaylistItem(playlist: PlayList) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A2A32)),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = Color(0xFF6FE4FF),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = playlist.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    text = "${playlist.totalSongs} canciones • ${playlist.durationLabel}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFFA9A9B2)
                    )
                )
            }

            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Reproducir",
                tint = Color(0xFF6FE4FF),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun EmptyPlaylistsState() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = Color(0xFF6FE4FF),
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "No tienes playlists",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Crea tu primera playlist para empezar",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFA9A9B2)
                )
            )
        }
    }
}