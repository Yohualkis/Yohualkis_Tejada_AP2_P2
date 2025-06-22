package com.ucne.yohualkis_tejada_ap2_p2.presentation.repositorio

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.RepositorioDto
import com.ucne.yohualkis_tejada_ap2_p2.presentation.composables.TopBarGenerica
import kotlinx.coroutines.launch

@Composable
fun ClientesListScreen(
    viewModel: RepositoriosViewModel = hiltViewModel(),
    nuevoCliente: () -> Unit,
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteListBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        nuevoRepo = nuevoCliente,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClienteListBody(
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
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    ClienteListHeader()
                    HorizontalDivider()
                }

                items(uiState.listaRepositorios) { cliente ->
                    ClienteListItem(cliente)
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
fun ClienteListHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Nombre", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(0.10f))
        Text(text = "Descripción", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(0.10f))
        Text(text = "Url", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(0.10f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteListItem(repositorio: RepositorioDto) {
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart) {
                coroutineScope.launch {}
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            SwipeBackground(dismissState)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = repositorio.name .toString(), modifier = Modifier.weight(0.12f))
            Text(text = repositorio.description.toString(), modifier = Modifier.weight(0.12f))
            Text(text = repositorio.htmlUrl.toString(), modifier = Modifier.weight(0.12f))
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(dismissState: SwipeToDismissBoxState) {
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            SwipeToDismissBoxValue.Settled -> Color.Transparent
            SwipeToDismissBoxValue.EndToStart -> Color.Red
            SwipeToDismissBoxValue.StartToEnd -> Color.Red
        },
        label = "Changing color"
    )
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
fun ClienteListPreview() {
    val list = listOf(
        RepositorioDto(
            name = "Repo 1",
            description = "Descripcion 1",
            htmlUrl = "https://www.github.com/usuario/repo1"
        ),
        RepositorioDto(
            name = "Repo 2",
            description = "Descripcion 2",
            htmlUrl = "https://www.github.com/usuario/repo2"
        )
    )

    ClienteListBody(
        uiState = RepositoriosUiState(listaRepositorios = list),
        onEvent = {},
        nuevoRepo = {},
        goBack = {}
    )
}