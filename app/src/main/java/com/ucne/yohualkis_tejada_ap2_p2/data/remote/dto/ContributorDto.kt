package com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto

import com.squareup.moshi.Json

data class ContributorDto(
    @Json(name = "login")
    val nombre: String?,

    @Json(name = "contributions")
    val vecesContribuidas: Int?,

    @Json(name = "html_url")
    val urlContribuyente: String?
)
