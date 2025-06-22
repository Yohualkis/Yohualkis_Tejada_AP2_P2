package com.ucne.yohualkis_tejada_ap2_p2.presentation.repositorio

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.RepositorioDto
import com.ucne.yohualkis_tejada_ap2_p2.presentation.composables.TopBarGenerica

@Composable
fun RepositorioListScreen(
    viewModel: RepositoriosViewModel = hiltViewModel(),
    nuevoRepo: () -> Unit,
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RepositorioListBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        nuevoRepo = nuevoRepo,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepositorioListBody(
    uiState: RepositoriosUiState,
    onEvent: (RepositorioEvent) -> Unit,
    nuevoRepo: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarGenerica(
                goBack = goBack,
                titulo = "Repositorios API"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    nuevoRepo()
                }
            ) {
                Icon(Icons.Filled.Add, "")
            }
        },
    ) { paddingValues ->
        val refreshing = uiState.isLoading
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { onEvent(RepositorioEvent.GetRepositorios("Yohualkis")) }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                items(uiState.listaRepositorios) { repositorio ->
                    RepositorioCard(repositorio)
                }

                item {
                    if (!uiState.errorMessage.isNullOrEmpty()) {
                        ShowErrorMessage(errorMessage = uiState.errorMessage)
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun RepositorioCard(repositorio: RepositorioDto) {
    var isExpanded by remember { mutableStateOf(false) }

    val descripcion = repositorio.description ?: "Sin descripción"
    val mostrarBoton = descripcion.length > 50
    val textoMostrado = if (isExpanded || !mostrarBoton) descripcion else descripcion.take(50) + "..."

    val primaryTextColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val accentColor = MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = repositorio.name ?: "Sin nombre",
                fontWeight = FontWeight.Bold,
                color = primaryTextColor
            )
            Text(
                text = textoMostrado,
                modifier = Modifier.padding(top = 4.dp),
                color = secondaryTextColor
            )
            if (mostrarBoton) {
                Text(
                    text = if (isExpanded) "▲ Mostrar menos" else "▼ Mostrar más",
                    color = accentColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { isExpanded = !isExpanded }
                )
            }
            Text(
                text = repositorio.htmlUrl ?: "Sin URL",
                modifier = Modifier.padding(top = 4.dp),
                color = accentColor
            )
        }
    }
}

@Composable
fun ShowErrorMessage(errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_LONG).show()
    }
}

@Preview(showBackground = true)
@Composable
fun RepositorioListPreview() {
    val list = listOf(
        RepositorioDto(
            name = "Repo 1",
            description = "Descripcion del repositorio 1",
            htmlUrl = "https://github.com/usuario/repo1"
        ),
        RepositorioDto(
            name = "Repo 2",
            description = "Descripcion del repositorio 246532564865132564865132548946532",
            htmlUrl = "https://github.com/usuario/repo2"
        )
    )

    RepositorioListBody(
        uiState = RepositoriosUiState(listaRepositorios = list),
        onEvent = {},
        nuevoRepo = {},
        goBack = {}
    )
}
