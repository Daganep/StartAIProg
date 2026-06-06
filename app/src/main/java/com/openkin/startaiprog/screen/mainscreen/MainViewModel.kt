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

    private val _viewState = MutableStateFlow<String>("")
    val viewState: StateFlow<String> = _viewState.asStateFlow()

    fun askGemini(question: String) {
        viewModelScope.launch(Dispatchers.IO) {
            geminiRepository.askGemini(question)
                .collect { answer ->
                    _viewState.update { answer }
                }
        }
    }
}
