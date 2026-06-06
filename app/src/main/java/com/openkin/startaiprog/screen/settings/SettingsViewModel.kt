package com.openkin.startaiprog.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.startaiprog.repository.IGeminiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val geminiRepository: IGeminiRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<SettingsViewState>(SettingsViewState())
    val viewState: StateFlow<SettingsViewState> = _viewState.asStateFlow()

    fun loadSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            geminiRepository.loadSettings().collect { storedSettings ->
                _viewState.update { storedSettings }
            }
        }
    }

    fun saveSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            geminiRepository.saveSettings(_viewState.value)
        }
    }

    fun updateMaxOutputTokens(newValue: String) {
        _viewState.update { it.copy(maxOutputTokens = newValue)  }
    }

    fun updateTemperature(newValue: String) {
        _viewState.update { it.copy(temperature = newValue)  }
    }

    fun updateTopP(newValue: String) {
        _viewState.update { it.copy(topP = newValue)  }
    }

    fun updateStopSequencesFlag(newValue: Boolean) {
        _viewState.update { it.copy(isStopSequencesEnabled = newValue)  }
    }

    fun updateStopSequences(newValue: String) {
        _viewState.update { it.copy(stopSequences = newValue)  }
    }
}
