package com.ucne.yohualkis_tejada_ap2_p2.presentation.contributors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.ContributorDto
import com.ucne.yohualkis_tejada_ap2_p2.presentation.composables.TopBarGenerica
import com.ucne.yohualkis_tejada_ap2_p2.presentation.repositorio.ShowErrorMessage

@Composable
fun ContributorsListScreen(
    viewmodel: ContributorViewModel = hiltViewModel(),
    nombreRepo: String,
    goBack: () -> Unit,
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    ContributorsList(
        uiState = uiState,
        onEvent = viewmodel::onEvent,
        goBack = goBack,
        nombreRepo = nombreRepo
    )
}

@Composable
fun ContributorsList(
    uiState: ContributorUiState,
    onEvent: (ContributorsEvent) -> Unit,
    goBack: () -> Unit,
    nombreRepo: String
) {
    Scaffold(
        topBar = {
            TopBarGenerica(
                goBack = goBack,
                titulo = "Contribuyentes"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ContributorsEvent.GetContributors("Yohualkis", nombreRepo))
                }
            ) {
                Icon(Icons.Filled.Refresh, "")
            }
        }
    ) { innerPading ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPading)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                items(uiState.listaContribuyentes){
                    ContributorCard(it)
                }

                item {
                    if (!uiState.errorMessage.isNullOrEmpty()) {
                        ShowErrorMessage(errorMessage = uiState.errorMessage)
                    }
                }
            }
        }
    }
}

@Composable
fun ContributorCard(
    contributor: ContributorDto,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = contributor.nombre ?: "N/A",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${contributor.vecesContribuidas ?: 0} contribuciones",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                Text(
                    text = contributor.urlContribuyente ?: "N/A",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewContributorCard() {
    ContributorCard(
        ContributorDto(
            nombre = "Prueba",
            vecesContribuidas = 100,
            urlContribuyente = "https://github.com/users/prueba"
        )
    )
}

@Composable
@Preview
fun PreviewContributorsListScreen() {
    val lista = listOf<ContributorDto>(
        ContributorDto(
            nombre = "Prueba1",
            vecesContribuidas = 100,
            urlContribuyente = "https://github.com/users/prueba1"
        ),
        ContributorDto(
            nombre = "Prueba2",
            vecesContribuidas = 200,
            urlContribuyente = "https://github.com/users/prueba2"
        ),
        
    )
    ContributorsList(
        uiState = ContributorUiState(listaContribuyentes = lista),
        onEvent = {},
        goBack = {},
        nombreRepo = "",
    )
}