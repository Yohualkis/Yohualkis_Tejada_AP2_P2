package com.ucne.yohualkis_tejada_ap2_p2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ucne.yohualkis_tejada_ap2_p2.presentation.repositorio.RepositorioListScreen

@Composable
fun GeneralNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ListaEntidades,
        modifier = modifier
    ) {
        composable<Screen.ListaEntidades>{
            RepositorioListScreen(
                nuevoRepo = {},
                goBack = { navHostController.navigateUp() }
            )
        }
    }
}