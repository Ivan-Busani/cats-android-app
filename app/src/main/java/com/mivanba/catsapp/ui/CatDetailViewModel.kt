package com.mivanba.catsapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mivanba.catsapp.data.Cat
import com.mivanba.catsapp.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CatDetailUiState {
    object Loading : CatDetailUiState
    data class Success(val cat: Cat) : CatDetailUiState
    data class Error(val message: String) : CatDetailUiState
}

class CatDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val catId: String = checkNotNull(savedStateHandle["catId"])

    private val _uiState = MutableStateFlow<CatDetailUiState>(CatDetailUiState.Loading)
    val uiState: StateFlow<CatDetailUiState> = _uiState.asStateFlow()

    init {
        fetchCatDetails()
    }

    private fun fetchCatDetails() {
        viewModelScope.launch {
            _uiState.value = CatDetailUiState.Loading
            try {
                val response = RetrofitClient.apiService.getCatDetails(catId)
                _uiState.value = CatDetailUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = CatDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}