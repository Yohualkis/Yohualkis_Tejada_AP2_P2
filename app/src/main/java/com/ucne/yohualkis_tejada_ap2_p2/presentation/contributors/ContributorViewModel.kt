package com.ucne.yohualkis_tejada_ap2_p2.presentation.contributors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.yohualkis_tejada_ap2_p2.data.remote.Resource
import com.ucne.yohualkis_tejada_ap2_p2.data.repositories.ContributorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContributorViewModel @Inject constructor(
    private val contributorRepository: ContributorRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContributorUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ContributorsEvent) {
        when (event) {
            is ContributorsEvent.GetContributors -> onGetContributors(
                event.username,
                event.nombreRepo
            )
        }
    }

    private fun onGetContributors(username: String, nombreRepo: String) {
        viewModelScope.launch {
            contributorRepository.getContributors(username, nombreRepo)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = result.message,
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    listaContribuyentes = result.data ?: emptyList(),
                                )
                            }
                        }
                    }
                }
        }
    }
}