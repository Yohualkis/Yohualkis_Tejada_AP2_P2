package com.ucne.yohualkis_tejada_ap2_p2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ListaRepositorios: Screen()

    @Serializable
    data class ListaContributors(val username: String, val nombreRepo: String): Screen()
}