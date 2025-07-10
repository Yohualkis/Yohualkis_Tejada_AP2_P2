package com.ucne.yohualkis_tejada_ap2_p2.presentation.repositorio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.Resource
import com.ucne.yohualkis_tejada_ap2_p2.data.repositories.RepositorioRepository
import com.ucne.yohualkis_tejada_ap2_p2.presentation.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class RepositoriosViewModel @Inject constructor(
    private val repository: RepositorioRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RepositoriosUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getRepositorios("Yohualkis")
    }

    fun onEvent(event: RepositorioEvent) {
        when (event) {
            is RepositorioEvent.GetRepositorios -> getRepositorios(event.username)
            RepositorioEvent.Delete -> {}
            is RepositorioEvent.DescriptionChange -> descriptionChange(event.description)
            is RepositorioEvent.HtmlUrlChange -> htmlUrlChange(event.htmlUrl)
            RepositorioEvent.Limpiar -> limpiar()
            RepositorioEvent.LimpiarErrores -> limpiarErrores()
            is RepositorioEvent.NameChange -> nameChange(event.name)
            RepositorioEvent.Save -> {}
        }
    }

    private fun limpiarErrores() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    nameError = "",
                    descriptionError = "",
                    htmlUrlError = "",
                )
            }
        }
    }

    private fun limpiarErrorMessageName() {

    }

    private fun limpiarErrorMessageDescription() {

    }

    private fun limpiarErrorMessageHtmlUrl() {

    }

    private fun descriptionChange(telefono: String) {

    }

    private fun nameChange(nombre: String) {

    }

    private fun htmlUrlChange(url: String) {

    }

    private fun limpiar() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    name = "",
                    description = "",
                    htmlUrl = "",
                    nameError = "",
                    descriptionError = "",
                    htmlUrlError = "",
                )
            }
        }
    }

    private fun getRepositorios(username: String) {
        viewModelScope.launch {
            repository.getRepositorios(username).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                listaRepositorios = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun RepositoriosUiState.toEntity() = RepositoriosUiState(
        name = name,
        description = description,
        htmlUrl = htmlUrl,
    )
}