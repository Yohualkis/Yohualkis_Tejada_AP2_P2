package com.ucne.yohualkis_tejada_ap2_p2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ListaEntidades: Screen()
}