package com.mas.flowlibre.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mas.flowlibre.presentation.components.*
import com.mas.flowlibre.presentation.viewModel.ArtistProfileViewModel

@Composable
fun ArtistProfileScreen(
    navController: NavController,
    artistId: Int,
    viewModel: ArtistProfileViewModel = viewModel()
) {
    val artistProfile by viewModel.artistProfile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    LaunchedEffect(artistId) {
            viewModel.loadArtistProfile(artistId)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0E))
            .padding(16.dp)
    ){
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF6FE4FF)
            )
        } else {
            artistProfile?.let { profile ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ArtistHeader(artist = profile.artist)
                    }


                    item {
                        SectionTitle(
                            title = "Canciones",
                            icon = Icons.Default.MusicNote
                        )
                    }


                    items(profile.songs) {song ->
                        SongItem(song = song)
                    }


                    item {
                        SectionTitle(
                            title = "Albums",
                            icon = Icons.Default.Album
                        )
                    }


                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(profile.albums) { album ->
                                AlbumItem(album=album)
                            }
                        }
                    }
                }
            }
        }
    }

}