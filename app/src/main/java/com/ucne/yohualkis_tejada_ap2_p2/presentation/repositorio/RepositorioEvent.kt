package com.ucne.yohualkis_tejada_ap2_p2.presentation.repositorio

sealed interface RepositorioEvent {
    data class NameChange(val name: String): RepositorioEvent
    data class DescriptionChange(val description: String): RepositorioEvent
    data class HtmlUrlChange(val htmlUrl: String): RepositorioEvent
    data class GetRepositorios(val username: String): RepositorioEvent

    data object Save: RepositorioEvent
    data object Delete: RepositorioEvent
    data object Limpiar: RepositorioEvent
    data object LimpiarErrores: RepositorioEvent
}