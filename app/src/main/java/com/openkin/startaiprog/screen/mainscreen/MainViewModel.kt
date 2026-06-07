package com.openkin.startaiprog.screen.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.startaiprog.repository.IGeminiRepository
import com.openkin.startaiprog.utils.EMPTY_STRING
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
                response = EMPTY_STRING,
                showPromt = false,
                isLoading = true,
                isError = false,
            ) }
            geminiRepository.askGemini(_viewState.value.promt)
                .collect { answer ->
                    _viewState.update { it.copy(
                        response = answer.message,
                        isLoading = false,
                        isError = answer.isError,
                    )}
                }
        }
    }
}
