package com.example.anfibios.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anfibios.AnfibiosApplication
import com.example.anfibios.data.AnfibiosRepository
import com.example.anfibios.model.Anfibio
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AnfibiosUiState {
    data class Success(val anfibios: List<Anfibio>) : AnfibiosUiState
    object Error : AnfibiosUiState
    object Loading : AnfibiosUiState
}

class AnfibiosViewModel(private val anfibiosRepository: AnfibiosRepository) : ViewModel() {
    var anfibiosUiState: AnfibiosUiState by mutableStateOf(AnfibiosUiState.Loading)
        private set

    init {
        getAnfibios()
    }

    fun getAnfibios() {
        viewModelScope.launch {
            anfibiosUiState = AnfibiosUiState.Loading
            anfibiosUiState = try {
                AnfibiosUiState.Success(anfibiosRepository.getAnfibios())
            } catch (e: IOException) {
                AnfibiosUiState.Error
            } catch (e: HttpException) {
                AnfibiosUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AnfibiosApplication)
                val anfibiosRepository = application.container.anfibiosRepository
                AnfibiosViewModel(anfibiosRepository = anfibiosRepository)
            }
        }
    }
}
