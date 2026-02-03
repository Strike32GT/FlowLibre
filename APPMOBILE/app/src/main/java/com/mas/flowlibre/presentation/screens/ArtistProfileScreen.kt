package com.mas.flowlibre.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mas.flowlibre.presentation.components.AlbumItem
import com.mas.flowlibre.presentation.components.ArtistHeader
import com.mas.flowlibre.presentation.components.SongItem
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

                    items(profile.albums) { album ->
                        AlbumItem(album=album)
                    }
                }
            }
        }
    }

}