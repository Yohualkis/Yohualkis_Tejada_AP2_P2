package com.ucne.yohualkis_tejada_ap2_p2.data.remote

import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.RepositorioDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{username}/repos")
    suspend fun listRepos(@Path("username") username: String): List<RepositorioDto>
}