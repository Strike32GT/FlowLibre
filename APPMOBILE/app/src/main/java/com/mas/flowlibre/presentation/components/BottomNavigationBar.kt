package com.mas.flowlibre.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mas.flowlibre.presentation.viewModel.HomeViewModel

@Composable
fun BottomNavigationBar(
    navController: NavController,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {
    Box(modifier = modifier) {
        val currentSong by homeViewModel.currentSong.collectAsState()
        if (currentSong != null) {
            MiniPlayer(
                song = currentSong!!,
                homeViewModel= homeViewModel,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset( y=(-60).dp)
                    .padding(horizontal = 16.dp)
                    .zIndex(10f)
                    .clickable{
                        navController.navigate("now_playing")
                    }
            )
        }
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationItem(
                    icon = Icons.Default.Home,
                    label = "Inicio",
                    isSelected =  selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )


                NavigationItem(
                    icon = Icons.Default.Search,
                    label = "Buscar",
                    isSelected =  selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )


                NavigationItem(
                    icon = Icons.Default.LibraryMusic,
                    label = "Biblioteca",
                    isSelected =  selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )


                NavigationItem(
                    icon = Icons.Default.Person,
                    label = "Perfil",
                    isSelected =  selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )
            }
        }
    }
}
