package com.openkin.startaiprog.presentation.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.startaiprog.domain.IChatRepository
import com.openkin.startaiprog.domain.IGeminiRepository
import com.openkin.startaiprog.domain.model.ChatMessageUI
import com.openkin.startaiprog.utils.EMPTY_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val geminiRepository: IGeminiRepository,
    private val chatRepository: IChatRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<ChatViewState>(ChatViewState())
    val viewState: StateFlow<ChatViewState> = _viewState.asStateFlow()

    fun loadChat(chatId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.loadChat(chatId).collect  { chat ->
                val messages = mutableListOf<ChatMessageUI>()
                chat.forEach { message ->
                    messages.add(
                        ChatMessageUI(
                            messageId = message.messageId,
                            message = message.message,
                            messageTime = message.timestamp,
                            outgoing = message.outgoing,
                        )
                    )
                }
                _viewState.update {
                    it.copy(chatId = chatId, messages = messages)
                }
            }
            chatRepository.getChatName(chatId).collect { chatName ->
                _viewState.update { it.copy(chatName = chatName) }
            }
        }
    }

    fun updatePromt(newPromt: String) {
        _viewState.update { it.copy(promt = newPromt) }
    }

    fun askGemini() {
        viewModelScope.launch(Dispatchers.IO) {
            val promt = _viewState.value.promt
            updatePromt(EMPTY_STRING)
            _viewState.update { it.copy(
                response = EMPTY_STRING,
                showPromt = false,
                isLoading = true,
                isError = false,
                totalTokens = EMPTY_STRING,
            ) }
            geminiRepository.askGemini(promt, _viewState.value.chatId)
        }
    }
}
