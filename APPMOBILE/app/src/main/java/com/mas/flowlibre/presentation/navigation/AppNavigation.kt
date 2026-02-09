package com.mas.flowlibre.presentation.navigation



import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable

import com.mas.flowlibre.presentation.components.BottomNavigationBar

import com.mas.flowlibre.presentation.screens.*



@Composable

fun AppNavigation(

    navController : NavHostController

) {

    NavHost(

        navController = navController,

        startDestination = "home"

    ) {



        composable("home") { Home(navController) }
        composable("crear_cuenta"){  CrearCuenta(navController)}
        composable("login") {
            Login(
                navController = navController,
                onNavigateToRegister = {
                    navController.navigate("crear_cuenta")
                }
            )
        }
        composable("buscar") { BuscarArtista(navController) }
        composable("biblioteca") { Biblioteca(navController) }
        composable("perfil") { Perfil(navController) }
        composable("artist_profile/{artistId}"){ backStackEntry ->

            val artistId = backStackEntry.arguments?.getString("artistId")?.toIntOrNull() ?: 0

            ArtistProfileScreen(

                navController = navController,

                artistId = artistId

            )

        }

    }

}





@Composable

fun BottomNavigationBarWithNavigation(

    navController: NavHostController,

    selectedTab: Int,

    onTabSelected: (Int) -> Unit,

    modifier : Modifier = Modifier

) {

    BottomNavigationBar(

        modifier = modifier,

        selectedTab = selectedTab,

        onTabSelected = { tab ->

            onTabSelected(tab)





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