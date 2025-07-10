package com.ucne.yohualkis_tejada_ap2_p2.presentation.contributors

import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.ContributorDto

data class ContributorUiState(
    val nombre: String? = null,
    val vecesContribuidas: Int? = null,
    val urlContribuyente: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val listaContribuyentes: List<ContributorDto> = emptyList(),
)
