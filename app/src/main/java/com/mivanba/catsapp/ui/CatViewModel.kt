package com.mivanba.catsapp.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mivanba.catsapp.data.Cat
import com.mivanba.catsapp.data.CatRepository
import com.mivanba.catsapp.data.RetrofitClient
import com.mivanba.catsapp.data.local.CatDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed interface CatUiState {
    object Loading : CatUiState
    data class Success(val cats: List<Cat>) : CatUiState
    data class Error(val message: String) : CatUiState
}

class CatViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<CatUiState>(CatUiState.Loading)
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _isFetchingMore = MutableStateFlow(false)
    val isFetchingMore: StateFlow<Boolean> = _isFetchingMore.asStateFlow()

    private val database = CatDatabase.getDatabase(application)
    private val repository = CatRepository(
        apiService = RetrofitClient.apiService,
        catDao = database.catDao()
    )

    private var currentPage = 0
    private var isFetching = false


    init {
        viewModelScope.launch {
            repository.cats.collectLatest { localCats ->
                if (localCats.isNotEmpty()) {
                    _uiState.value = CatUiState.Success(localCats)
                }
            }
        }

        getCats()
    }

    private fun getCats() {
        viewModelScope.launch {
            if (_uiState.value !is CatUiState.Success) {
                _uiState.value = CatUiState.Loading
            }
            fetchData(isRefresh = false)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            currentPage = 0
            try {
                fetchData(isRefresh = true)
            } catch(e: Exception) {
                // Error is managed in fetchData
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun loadMode() {
        if (isFetching) return

        viewModelScope.launch {
            currentPage++
            fetchData(isRefresh = false)
        }
    }

    private suspend fun fetchData(isRefresh: Boolean) {
        isFetching = true

        if (!isRefresh && _uiState.value is CatUiState.Success) {
            _isFetchingMore.value = true
        }

        try {
            repository.refreshCats(page = currentPage, isRefresh = isRefresh)
        } catch (e: Exception) {
            if (_uiState.value !is CatUiState.Success) {
                _uiState.value = CatUiState.Error(e.message ?: "Error desconocido")
            } else {
                Toast.makeText(
                    getApplication(),
                    "Error de conexión: Mostrando datos locales",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } finally {
            isFetching = false
            _isFetchingMore.value = false
        }
    }
}