package com.example.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansImagesApp
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.model.Amphiby
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AmphibyUiState {
    data class Success(val amphibians: List<Amphiby>) : AmphibyUiState
    object Error: AmphibyUiState
    object Loading: AmphibyUiState
}

class AmphibiansViewModel(
    private val amphibiansRepository: AmphibiansRepository
) : ViewModel() {
    var amphibiansUiState: AmphibyUiState by mutableStateOf(AmphibyUiState.Loading)
        private set

    init {
        getAmphibiansImages()
    }

    fun getAmphibiansImages(){
        viewModelScope.launch {
            amphibiansUiState = AmphibyUiState.Loading
            amphibiansUiState = try {
                AmphibyUiState.Success(amphibiansRepository.getAmphibyImage())
            }catch (e: IOException){
                AmphibyUiState.Error
            }catch (e: HttpException){
                AmphibyUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansImagesApp)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}