package com.openkin.startaiprog.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.startaiprog.repository.IChatRepository
import com.openkin.startaiprog.repository.IGeminiRepository
import com.openkin.startaiprog.screen.chat.model.ChatMessageUI
import com.openkin.startaiprog.utils.EMPTY_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(
    private val geminiRepository: IGeminiRepository,
    private val chatRepository: IChatRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<ChatViewState>(ChatViewState())
    val viewState: StateFlow<ChatViewState> = _viewState.asStateFlow()
    private var timer: Job? = null

    fun loadChat(chatId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.loadChat().collect  { chat ->
                _viewState.update {
                    it.copy(messages = chat.messages)
                }
            }
        }
    }

    fun updatePromt(newPromt: String) {
        _viewState.update { it.copy(promt = newPromt) }
    }

    fun changeTab() {
        _viewState.update { it.copy(showPromt = !it.showPromt) }
    }

    fun askGemini() {
        val newId = UUID.randomUUID().toString()
        val currentTimeMS = System.currentTimeMillis()
        val message = _viewState.value.promt
        val chatMessage = ChatMessageUI(
            messageId = newId,
            message = message,
            messageTime = currentTimeMS,
            outgoing = true,
        )
        viewModelScope.launch(Dispatchers.IO) {
            startTimer()
            _viewState.update { it.copy(
                response = EMPTY_STRING,
                showPromt = false,
                isLoading = true,
                isError = false,
                totalTokens = EMPTY_STRING,
            ) }
            geminiRepository.askGemini(chatMessage)
                .collect { answer ->
                    timer?.cancel()
                    _viewState.update { it.copy(
                        response = answer.message.message,
                        isLoading = false,
                        isError = answer.isError,
                        totalTokens = answer.totalTokensCount,
                    )}
                }
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            while (true) {
                val elapsedMs = System.currentTimeMillis() - startTime
                _viewState.update { it.copy(timerValue = elapsedMs) }
                delay(30)
            }
        }
    }
}
