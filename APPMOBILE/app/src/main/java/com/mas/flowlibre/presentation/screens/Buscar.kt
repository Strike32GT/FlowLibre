package com.mas.flowlibre.presentation.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.*

import androidx.compose.foundation.shape.*

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.*

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.*

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController

import coil3.compose.AsyncImage

import com.mas.flowlibre.domain.model.Artist

import com.mas.flowlibre.presentation.navigation.BottomNavigationBarWithNavigation

import com.mas.flowlibre.presentation.viewModel.*





@Composable

fun SectionTitleBuscar(

    icon: ImageVector,

    title: String

) {

    Row(

        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier.padding(vertical = 8.dp)

    ){

        Icon(

            imageVector = icon,

            contentDescription = null,

            tint = Color(0xFF6FE4FF),

            modifier = Modifier.size(20.dp)

        )



        Spacer(modifier = Modifier.width(8.dp))



        Text(

            text = title,

            style = MaterialTheme.typography.titleMedium.copy(

                fontWeight = FontWeight.SemiBold,

                color = Color.White

            )

        )

    }

}





@Composable

fun EmptyStateBuscar(

    icon: ImageVector,

    title: String,

    description: String

) {

    Card(

        modifier = Modifier

            .fillMaxWidth(),

        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),

        shape = RoundedCornerShape(12.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)

    ) {

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier

                .fillMaxWidth()

                .padding(32.dp)

        ){

            Icon(

                imageVector = icon,

                contentDescription = null,

                modifier = Modifier.size(48.dp),

                tint = Color(0xFF6FE4FF)

            )





            Spacer(modifier = Modifier.height(16.dp))





            Text(

                text = title,

                style = MaterialTheme.typography.titleMedium.copy(

                    fontWeight = FontWeight.SemiBold,

                    color = Color.White

                )

            )



            Spacer(modifier = Modifier.height(8.dp))



            Text(

                text = description,

                style = MaterialTheme.typography.bodyMedium.copy(

                    color = Color(0xFFA9A9B2)

                ),

                textAlign = TextAlign.Center

            )

        }

    }

}





@Composable

fun BuscarArtista(

    navController: NavHostController,

    viewModel: ArtistViewModel = viewModel()

) {

    var selectedTab by remember { mutableStateOf(1) }

    val artists by viewModel.artists.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()


    LaunchedEffect(searchQuery) {
        viewModel.onQueryChange(searchQuery)
    }






    Box(

        modifier = Modifier

            .fillMaxSize()

            .background(Color(0xFF0B0B0E))

    ){

        LazyColumn(

            modifier = Modifier

                .fillMaxSize()

                .background(Color(0xFF0B0B0E))

                .padding(horizontal = 16.dp)

                .padding(bottom = 80.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ){

            item {

                Spacer(modifier = Modifier.height(45.dp).statusBarsPadding())

            }



            item {

                SearchBar(

                    query = searchQuery,

                    onQueryChange = { searchQuery = it },

                    onSearch = {/**/}

                )

            }



            item {

                SectionTitleBuscar(

                    icon = Icons.Default.Person,

                    title = if (searchQuery.isBlank()) "Artistas Populares" else "Resultados de Búsqueda"

                )
            }



            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(
                            color = Color(0xFF6FE4FF),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }





            if(artists.isEmpty()) {

                item {

                    EmptyStateBuscar(

                        icon = Icons.Default.PersonSearch,

                        title = "No hay artistas disponibles",

                        description = "Los artistas que apareceran aqui cuando estén disponibles"

                    )

                }

            } else {

                items(artists) { artist ->

                    ArtistItem(
                        artist = artist,
                        onArtistClick = { artistId ->
                            navController.navigate("artist_profile/\$artistId")
                        })

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

fun SearchBar(

    query: String,

    onQueryChange: (String) -> Unit,

    onSearch: () -> Unit

) {

    Card(

        modifier = Modifier

            .fillMaxWidth(),

        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),

        shape = RoundedCornerShape(12.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)

    ){

        Row(

            modifier = Modifier

                .fillMaxWidth()

                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(

                imageVector = Icons.Default.Search,

                contentDescription = "Buscar",

                tint = Color(0xFFA9A9B2),

                modifier = Modifier.size(24.dp)

            )



            Spacer(modifier = Modifier.width(12.dp))





            TextField(

                value = query,

                onValueChange = onQueryChange,

                placeholder = {

                    Text(

                        text = "Buscar artistas...",

                        color = Color(0xFFA9A9B2)

                    )

                },

                colors = TextFieldDefaults.colors(

                    unfocusedContainerColor = Color.Transparent,

                    focusedContainerColor = Color.Transparent,

                    unfocusedIndicatorColor = Color.Transparent,

                    focusedIndicatorColor = Color.Transparent

                ),



                textStyle = MaterialTheme.typography.bodyLarge.copy(

                    color = Color.White

                ),



                modifier = Modifier.fillMaxWidth(),

                singleLine = true

            )

        }

    }

}





@Composable

fun ArtistItem(
    artist: Artist,
    onArtistClick: (Int) -> Unit = {}
) {

    Card(

        modifier = Modifier

            .fillMaxWidth()
            .clickable{onArtistClick(artist.id)},

        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),

        shape = RoundedCornerShape(12.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)

    ){

        Row(

            modifier = Modifier

                .fillMaxWidth()

                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically

        ){

            AsyncImage(

                model = artist.profileImageUrl.ifEmpty { "" },

                contentDescription = "",

                modifier = Modifier

                    .size(56.dp)

                    .clip(CircleShape)

                    .background(Color(0xFF2E2E35)),

                contentScale = ContentScale.Crop

            )



            Spacer(modifier = Modifier.width(16.dp))





            Column(

                modifier = Modifier.weight(1f)

            ){

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ){

                    Text(

                        text = artist.name,

                        style = MaterialTheme.typography.bodyLarge.copy(

                            fontWeight = FontWeight.SemiBold,

                            color = Color.White

                        )

                    )



                    if (artist.verified) {

                        Spacer( modifier = Modifier.width(4.dp))

                        Icon(

                            imageVector = Icons.Default.Verified,

                            contentDescription = "Verificado",

                            tint = Color(0xFF6FE4FF),

                            modifier = Modifier.size(16.dp)

                        )

                    }

                }





                Text(

                    text = "${formatFollowers(artist.followers.toLong())} seguidores",

                    style = MaterialTheme.typography.bodySmall.copy(

                        color = Color(0xFFA9A9B2)

                    )

                )

            }



            IconButton(

                onClick = {/**/}

            ) {

                Icon(

                    imageVector = Icons.Default.MoreVert,

                    contentDescription = "Mas Opciones",

                    tint = Color(0xFFA9A9B2)

                )

            }

        }

    }

}





