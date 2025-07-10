package com.ucne.yohualkis_tejada_ap2_p2.data.repositories

import com.ucne.yohualkis_tejada_ap2_p2.data.remote.RemoteDataSource
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.Resource
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.ContributorDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContributorRepository @Inject constructor(
    private val remote: RemoteDataSource,
) {
    fun getContributors(
        username: String,
        nombreRepo: String
    ): Flow<Resource<List<ContributorDto>>> = flow {
        try {
            emit(Resource.Loading())
            val listaContributors = remote.getContributors(username, nombreRepo)
            emit(Resource.Success(listaContributors))
        } catch (e: Exception) {
            emit(Resource.Error("Error al obtener los contribuyentes: ${e.message}"))
        }
    }
}