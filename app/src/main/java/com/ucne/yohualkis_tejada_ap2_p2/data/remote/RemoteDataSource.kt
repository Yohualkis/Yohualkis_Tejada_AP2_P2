package com.ucne.yohualkis_tejada_ap2_p2.data.remote

import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.RepositorioDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gitHubApi: GitHubApi
){
    suspend fun getRepositorios(username: String): List<RepositorioDto> = gitHubApi.listRepos(username)
}