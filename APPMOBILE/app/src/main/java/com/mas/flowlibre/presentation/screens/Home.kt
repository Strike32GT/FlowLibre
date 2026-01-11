package com.mas.flowlibre.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.presentation.viewModel.HomeViewModel

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
    onSongClick: (Song) -> Unit = {}
) {
    val songs by viewModel.songs.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0E))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Header()
        }
    }
}

@Composable
private fun Header() {
    Column {

    }
}