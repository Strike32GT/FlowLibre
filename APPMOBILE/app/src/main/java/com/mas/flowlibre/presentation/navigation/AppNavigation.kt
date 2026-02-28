package com.mas.flowlibre.presentation.navigation



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.mas.flowlibre.presentation.components.*
import com.mas.flowlibre.presentation.screens.*
import com.mas.flowlibre.presentation.viewModel.HomeViewModel



@Composable

fun AppNavigation(
    homeViewModel: HomeViewModel
) {
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf(-1) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        selectedTab = when (currentRoute) {
            "home" -> 0
            "buscar" -> 1
            "biblioteca" -> 2
            "perfil" -> 3
            else -> selectedTab
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("home") {
            Home(navController = navController, homeViewModel = homeViewModel)
        }


        composable("crear_cuenta"){
            CrearCuenta(navController)
        }


        composable("login") {
            Login(
                navController = navController,
                onLoginSuccess = {
                    selectedTab = 0
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("crear_cuenta")
                }
            )
        }


        composable("buscar") {
            BuscarArtista(navController = navController, homeViewModel = homeViewModel)
        }


        composable("biblioteca") {
            Biblioteca(navController = navController, homeViewModel = homeViewModel)
        }


        composable("perfil") {
            Perfil(navController = navController, homeViewModel = homeViewModel)
        }





        composable("artist_profile/{artistId}"){ backStackEntry ->
            val artistId = backStackEntry.arguments?.getString("artistId")?.toIntOrNull() ?: 0
            ArtistProfileScreen(
                navController = navController,
                artistId = artistId
            )
        }


        composable("now_playing") {
            NowPlayingScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }
    }


    if (selectedTab >=0) {
        BottomNavigationBarWithNavigation(
            navController = navController,
            selectedTab = selectedTab,
            onTabSelected = { tab -> selectedTab = tab },
            homeViewModel = homeViewModel,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
  }
}





@Composable

fun BottomNavigationBarWithNavigation(
    navController: NavHostController,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier : Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {
    BottomNavigationBar(
        navController = navController,
        modifier = modifier,
        selectedTab = selectedTab,
        homeViewModel = homeViewModel,
        onTabSelected = { tab ->
            val destination = when (tab) {
                0 -> "home"
                1 -> "buscar"
                2 -> "biblioteca"
                3 -> "perfil"
                else -> "home"
            }

            navController.navigate(destination) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}