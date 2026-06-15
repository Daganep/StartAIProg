package com.openkin.startaiprog.presentation.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.startaiprog.domain.IChatRepository
import com.openkin.startaiprog.domain.IGeminiRepository
import com.openkin.startaiprog.domain.ISettingsRepository
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
    private val settingsRepository: ISettingsRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<ChatViewState>(ChatViewState())
    val viewState: StateFlow<ChatViewState> = _viewState.asStateFlow()

    fun loadChat(chatId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.loadMessages(chatId).collect  { chat ->
                val messages = mutableListOf<ChatMessageUI>()
                chat.forEach { message ->
                    messages.add(
                        ChatMessageUI(
                            messageId = message.messageId,
                            message = message.message,
                            messageTime = message.timestamp,
                            outgoing = message.outgoing,
                            tokensCount = message.tokensCount,
                        )
                    )
                }
                _viewState.update {
                    it.copy(chatId = chatId, messages = messages)
                }
            }
        }
        viewModelScope.launch {
            chatRepository.getChat(chatId).collect {
                it?.let { chat ->
                    _viewState.update { viewState ->
                        viewState.copy(
                            chatName = chat.title,
                            totalTokens = chat.totalTokens.toString(),
                        )
                    }
                }
            }
        }
        viewModelScope.launch {
            settingsRepository.loadChatSettings().collect { showTokens ->
                _viewState.update { it.copy(showTokens = showTokens) }
            }
        }
    }

    fun updatePromt(newPromt: String) {
        _viewState.update { it.copy(promt = newPromt) }
    }

    fun updateShowTokenFlag() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.updateShowTokenFlag(!_viewState.value.showTokens)
        }
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
