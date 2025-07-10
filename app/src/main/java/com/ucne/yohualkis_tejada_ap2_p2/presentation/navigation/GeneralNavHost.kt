package com.ucne.yohualkis_tejada_ap2_p2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ucne.yohualkis_tejada_ap2_p2.presentation.contributors.ContributorsListScreen
import com.ucne.yohualkis_tejada_ap2_p2.presentation.repositorio.RepositorioListScreen

@Composable
fun GeneralNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ListaRepositorios,
        modifier = modifier
    ) {

        // LISTA REPOSITORIOS
        composable<Screen.ListaRepositorios>{
            RepositorioListScreen(
                nuevoRepo = {},
                goBack = { navHostController.navigateUp() },
                goToContributor = { username, nombreRepo ->
                    navHostController.navigate(Screen.ListaContributors(username, nombreRepo))
                }
            )
        }

        // LISTA CONTRIBUYENTES
        composable<Screen.ListaContributors> {
            val args = it.toRoute<Screen.ListaContributors>()
            ContributorsListScreen(
                nombreRepo = args.nombreRepo,
                goBack = {
                    navHostController.navigateUp()
                },
            )
        }
    }
}