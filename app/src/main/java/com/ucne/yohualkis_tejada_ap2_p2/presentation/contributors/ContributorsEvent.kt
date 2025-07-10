package com.ucne.yohualkis_tejada_ap2_p2.presentation.contributors

sealed interface ContributorsEvent {
    data class GetContributors(val username: String, val nombreRepo: String): ContributorsEvent
}