package com.mas.flowlibre.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.mas.flowlibre.domain.model.Song

@Composable
fun SongList(
    songs: List<Song>,
    onSongClick : (Song) -> Unit = {},
    formatTime: (Long) -> String
) {
    LazyColumn(
        modifier = Modifier.height(300.dp)
    ) {
        items(songs) {song ->
            SongItem(
                song = song,
                onClick = { onSongClick(song) },
                formatTime = formatTime
            )
        }
    }
}


@Composable
private fun SongItem(
    song: Song,
    onClick: () -> Unit,
    formatTime : (Long) -> String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "http://10.0.2.2:8000${song.coverUrl}",
            contentDescription = song.title,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

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

        Text(
            text = formatTime(song.duration.toLong() * 1000),
            color = Color(0xFFA9A9B2),
            style = MaterialTheme.typography.bodySmall
        )
    }
}