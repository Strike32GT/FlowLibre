package com.mas.flowlibre.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mas.flowlibre.data.model.Playlist
import com.mas.flowlibre.presentation.components.SongList
import com.mas.flowlibre.presentation.navigation.BottomNavigationBarWithNavigation
import com.mas.flowlibre.presentation.viewModel.HomeViewModel
import com.mas.flowlibre.presentation.viewModel.LibraryViewModel
import com.mas.flowlibre.presentation.viewModel.LoadPlaylistSongsState

@Composable
fun Biblioteca(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    viewModel: LibraryViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(2) }
    val playlists by viewModel.playlists.collectAsState()
    val userPlaylists by viewModel.userPlaylists.collectAsState()
    val createPlaylistState by viewModel.createPlaylistState.collectAsState()
    val loadPlaylistSongsState by viewModel.loadPlaylistSongsState.collectAsState()
    var showCreatePlaylistDialog by remember { mutableStateOf(false) }
    var playlistName by remember { mutableStateOf("")}
    val snackbarHostState = remember { SnackbarHostState() }
    val homeviewmodel : HomeViewModel = viewModel()
    val context = LocalContext.current
    val currentLoadState = viewModel.loadPlaylistSongsState.collectAsState().value
    var showPlaylistSongsDialog  by remember { mutableStateOf(false) }
    var selectedPlaylistForSongs by remember { mutableStateOf<Playlist?>(null) }


    LaunchedEffect(loadPlaylistSongsState) {
        val state = loadPlaylistSongsState
        when (state) {
            is LoadPlaylistSongsState.Success -> {
                if (state.songs.isNotEmpty()){
                    homeviewmodel.playSong(context, state.songs.first())
                }
            }
            is LoadPlaylistSongsState.Error -> {
                snackbarHostState.showSnackbar("Error ${state.message}")
            }
            else -> {/**/}
        }

    }

    LaunchedEffect(Unit) {
        viewModel.loadUserPlaylists()
    }


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
                            text = viewModel.getTotalListeningTime(),
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
                            text = "${userPlaylists.size}",
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PlayList",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )

                    IconButton(
                        onClick = { showCreatePlaylistDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Crear PlayList",
                            tint = Color.White
                        )
                    }
                }
            }

            if (userPlaylists.isEmpty()) {
                item {
                    EmptyPlaylistsState()
                }
            } else {
                items(userPlaylists) { playlist ->
                    UserPlaylistItem(
                        playlist = playlist,
                        libraryViewModel = viewModel,
                        homeViewModel = homeviewmodel,
                        onPlaylistClick = {
                            selectedPlaylistForSongs = playlist
                            showPlaylistSongsDialog = true
                            viewModel.loadPlaylistSongs(playlist.id)
                        }
                    )
                }
            }


            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }


        if (showCreatePlaylistDialog) {
            AlertDialog(
                onDismissRequest = {
                    showCreatePlaylistDialog = false
                    playlistName = ""
                },
                title = {
                    Text("Crear una nueva Playlist")
                }, text = {
                    OutlinedTextField(
                        value = playlistName,
                        onValueChange = { playlistName = it },
                        label = { Text("Nombre de PlayList")},
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (playlistName.isNotBlank()) {
                                viewModel.createPlaylist(playlistName)
                                showCreatePlaylistDialog = false
                                playlistName = ""
                            }
                        },
                        enabled = playlistName.isNotBlank()
                    ) {
                        Text("Crear")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showCreatePlaylistDialog = false
                            playlistName = ""
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }



        if (showPlaylistSongsDialog && selectedPlaylistForSongs != null) {
            AlertDialog(
                onDismissRequest = {
                    showPlaylistSongsDialog = false
                    selectedPlaylistForSongs = null
                },
                title = {
                    Text(
                        text = selectedPlaylistForSongs!!.name,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    val playlistSongs by viewModel.playlistSongs.collectAsState()

                    when (currentLoadState) {
                        is LoadPlaylistSongsState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ){
                                CircularProgressIndicator(color = Color(0xFF6FE4FF))
                            }
                        }

                        is LoadPlaylistSongsState.Success -> {
                            if (playlistSongs.isEmpty()) {
                                Text(
                                    text = "Esta playlist no tiene canciones",
                                    color = Color(0xFFA9A9B2)
                                )
                            } else {
                                SongList(
                                    songs = playlistSongs,
                                    formatTime = { viewModel.formatTime(it) }
                                )
                            }
                        }

                        is LoadPlaylistSongsState.Error -> {
                            Text(
                                text = "Error: ${currentLoadState.message}",
                                color = Color.Red
                            )
                        }

                        else -> {
                            Text(
                                text = "Cargando ...",
                                color = Color(0xFFA9A9B2)
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val playlistSongs = viewModel.playlistSongs.value
                            if (playlistSongs.isNotEmpty()) {
                                homeviewmodel.playSong(context, playlistSongs.first())
                            }
                            showPlaylistSongsDialog = false
                            selectedPlaylistForSongs = null
                        }
                    ) {
                        Text(
                            text = "Reproducir",
                            color = Color(0xFF6FE4FF)
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showPlaylistSongsDialog = false
                            selectedPlaylistForSongs = null
                        }
                    ) {
                        Text(
                            text = "Cerrar",
                            color = Color(0xFFA9A9B2)
                        )
                    }
                },
                containerColor = Color(0xFF15151B),
                tonalElevation = 8.dp
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        )
    }
}


@Composable
fun UserPlaylistItem(
    playlist: Playlist,
    libraryViewModel: LibraryViewModel,
    homeViewModel: HomeViewModel,
    onPlaylistClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onPlaylistClick()},
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
                    text = "Creada: ${playlist.created_at.take(10)}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFFA9A9B2)
                    )
                )
            }


            IconButton(
                onClick = {
                    libraryViewModel.loadPlaylistSongs(playlist.id)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Reproducir",
                    tint = Color(0xFF6FE4FF),
                    modifier = Modifier.size(24.dp)
                )
            }
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