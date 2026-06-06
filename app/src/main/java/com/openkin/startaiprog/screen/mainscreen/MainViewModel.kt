package com.openkin.startaiprog.screen.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.startaiprog.repository.IGeminiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val geminiRepository: IGeminiRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<MainViewState>(MainViewState())
    val viewState: StateFlow<MainViewState> = _viewState.asStateFlow()

    fun updatePromt(newPromt: String) {
        _viewState.update { it.copy(promt = newPromt) }
    }

    fun changeTab() {
        _viewState.update { it.copy(showPromt = !it.showPromt) }
    }

    fun askGemini() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.copy(
                showPromt = false,
                isLoading = true,
            ) }
            geminiRepository.askGemini(_viewState.value.promt)
                .collect { answer ->
                    _viewState.update { it.copy(
                        response = answer,
                        isLoading = false,
                    ) }
                }
        }
    }
}
