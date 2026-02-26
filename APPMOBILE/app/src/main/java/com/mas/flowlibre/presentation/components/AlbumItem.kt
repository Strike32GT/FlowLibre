package com.mas.flowlibre.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.*
import coil3.compose.AsyncImage
import com.mas.flowlibre.data.model.AlbumDto
@Composable
fun AlbumItem(album: AlbumDto) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24))
    ) {
        Column{
            AsyncImage(
                model = "http://10.0.2.2:8000" + album.cover_url,
                contentDescription = album.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )


            Text(
                text = album.title,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}