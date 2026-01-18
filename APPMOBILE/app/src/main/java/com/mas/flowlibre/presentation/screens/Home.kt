package com.mas.flowlibre.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mas.flowlibre.domain.model.Song
import com.mas.flowlibre.presentation.viewModel.HomeViewModel

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
) {
    val context = LocalContext.current
    val songs by viewModel.songs.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    var isPlayerVisible by remember {mutableStateOf(false)}
    var isPlaying by remember {mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0E))
    ){
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

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFF6FE4FF),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Recomendado para ti",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                }
            }

            item {
                RecommendedGrid(
                    songs = songs,
                    onSongClick = { song ->
                        viewModel.playSong(context, song)
                        isPlayerVisible = true
                        isPlaying = true
                    }
                )
            }


            item {
                SectionTitle(
                    icon = Icons.Default.ShowChart,
                    title = "Tendencias"
                )
            }

            items(songs.take(4)) { song ->
                PopularItem(
                    song = song,
                    onClick = {
                        viewModel.playSong(context,song)
                        isPlayerVisible = true
                        isPlaying = true
                    }
                )
            }

            item {
                SectionHeaderWithAction(
                    title = "Nuevos Lanzamientos",
                    onClickMore = {
                        //
                    }
                )
            }

            item {
                NewReleaseCarousel(
                    songs = songs,
                    onSongClick = {
                            song -> viewModel.playSong(context,song)
                        isPlayerVisible = true
                        isPlaying = true
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        if(isPlayerVisible && currentSong != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                val song = currentSong!!
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF15151B))
                ) {
                    Row (
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = "http://ip:8000${song.coverUrl}", // ip del celular
                            contentDescription = song.title,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ){
                            Text(song.title, color = Color.White)
                            Text(song.artistName, color = Color.Gray)

                            LinearProgressIndicator(
                                progress = { 0.3f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                if (isPlaying) {
                                    viewModel.pauseSong()
                                    isPlaying = false
                                } else {
                                    viewModel.resumeSong()
                                    isPlaying = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Column {
        Text(
            text = "Buenas noches",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Que quieres escuchar hoy?",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFFA9A9B2)
            )
        )
    }
}



@Composable
fun SectionTitle(
    icon : ImageVector? = null,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        if(icon != null){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6FE4FF),
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
        }
    }
}


@Composable
private fun RecommendedGrid(
    songs: List<Song>,
    onSongClick: (Song) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = false,
        modifier = Modifier.height(420.dp)
    ) {
        items(songs){ song ->
            RecommendedCard(
                song = song,
                onClick = {onSongClick(song)}
            )
        }
    }
}


@Composable
fun RecommendedCard(
    song: Song,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF15151B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            AsyncImage(
                model = "http://ip:8000" + song.coverUrl, //ip del celular
                contentDescription = song.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.25f)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = song.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


            Spacer(modifier = Modifier.height(2.dp))


            Text(
                text = song.artistName,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFFA9A9B2)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}



@Composable
fun TrendItem(song: Song, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF15151B)),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ){
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = "http://ip:8000" + song.coverUrl, //ip del celular
                contentDescription = song.title,
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = song.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = song.artistName,
                    color = Color(0xFFA9A9B2),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Composable
fun PopularItem(song: Song, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(Color(0xFF15151B)),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ){
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = "http://ip:8000" + song.coverUrl, //ip del celular
                contentDescription = song.title,
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))


            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text =song.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text =song.artistName,
                    color = Color(0xFFA9A9B2)
                )
            }

            Text(
                text = "Popular",
                color = Color.Red,
                fontSize = MaterialTheme.typography.labelSmall.fontSize
            )
        }
    }
}


@Composable
fun SectionHeaderWithAction(
    title:String,
    onClickMore: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        )

        TextButton(onClick = onClickMore) {
            Text(
                text = "Ver Mas",
                color = Color(0xFF6FE4FF)
            )
        }
    }
}


@Composable
fun NewReleaseCarousel(
    songs: List<Song>,
    onSongClick: (Song) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ){
        items(songs) { song ->
            NewReleaseCard(song, onSongClick)
        }
    }
}


@Composable
fun NewReleaseCard(
    song: Song,
    onClick: (Song) -> Unit
) {
    Card(
        onClick = {onClick(song) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF15151B)),
        modifier = Modifier.width(140.dp)
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ){
            AsyncImage(
                model = "http://ip:8000" + song.coverUrl, //ip del celular
                contentDescription = song.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = song.title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
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
    }
}


