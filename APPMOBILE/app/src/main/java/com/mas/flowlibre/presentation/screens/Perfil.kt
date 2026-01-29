package com.mas.flowlibre.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mas.flowlibre.presentation.navigation.BottomNavigationBarWithNavigation

@Composable
fun Perfil(
    navController: NavHostController
) {
    var selectedTab by remember { mutableStateOf(3) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0E))
    ) {
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
                ProfileHeader()
            }

            item {
                SectionTitlePerfil(
                    icon = Icons.Default.QueueMusic,
                    title = "PlayList"
                )
            }

            item {
                EmptyState(
                    icon = Icons.Default.QueueMusic,
                    title = "No hay playlist disposnibles",
                    description = "Tus playlists aparecerán aquí cuando estén disponibles"
                )
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
fun ProfileHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        Card(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24))
        ){
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                tint = Color(0xFF6FE4FF)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tu perfil",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )

        Text(
            text = "@username",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFA9A9B2)
            )
        )
    }
}


@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    description: String
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
            modifier = Modifier.size(64.dp),
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


@Composable
fun SectionTitlePerfil(
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