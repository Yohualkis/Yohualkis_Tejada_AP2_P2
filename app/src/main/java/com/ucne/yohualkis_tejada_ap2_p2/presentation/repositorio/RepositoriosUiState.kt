package com.ucne.yohualkis_tejada_ap2_p2.presentation.repositorio

import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.RepositorioDto

data class RepositoriosUiState(
    val name: String? = "",
    val description: String? = "",
    val htmlUrl: String? = "",
    val nameError: String? = "",
    val descriptionError: String? = "",
    val htmlUrlError: String? = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = "",
    val listaRepositorios: List<RepositorioDto> = emptyList()
)
