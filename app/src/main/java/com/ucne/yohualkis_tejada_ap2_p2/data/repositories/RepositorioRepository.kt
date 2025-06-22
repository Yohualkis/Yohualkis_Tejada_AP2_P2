package com.ucne.yohualkis_tejada_ap2_p2.data.repositories

import android.util.Log
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.RemoteDataSource
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.Resource
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.dto.RepositorioDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RepositorioRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getRepositorios(username: String): Flow<Resource<List<RepositorioDto>>> = flow {
        emit(Resource.Loading())
        try {
            val listaRepos = remoteDataSource.getRepositorios(username)
            emit(Resource.Success(listaRepos))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            Log.e("RepositorioRepository", "HttpException: $errorMessage")
            emit(Resource.Error("Error de conexion $errorMessage"))
        } catch (e: Exception) {
            emit(Resource.Error("Error al obtener los repositorios: ${e.message}"))
        }
    }
}