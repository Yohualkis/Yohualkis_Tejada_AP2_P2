package com.ucne.yohualkis_tejada_ap2_p2.data.remote

import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.ContributorDto
import retrofit2.http.GET
import retrofit2.http.Path


interface ContributorApi {
    @GET("repos/{username}/{nombre_repo}/contributors")
    suspend fun getContributors(
        @Path("username") username: String,
        @Path("nombre_repo") nombreRepo: String
    ): List<ContributorDto>
}