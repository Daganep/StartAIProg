package com.openkin.startaiprog.presentation.screen.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.startaiprog.domain.IChatRepository
import com.openkin.startaiprog.domain.model.ChatUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val chatRepository: IChatRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<MainViewState>(MainViewState())
    val viewState: StateFlow<MainViewState> = _viewState.asStateFlow()

    fun loadChats() {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.loadChats().collect { chats ->
                val loadedChats = mutableListOf<ChatUI>()
                chats.forEach {
                    loadedChats.add(
                        ChatUI(
                            chatId = it.chatId,
                            chatName = it.title,
                            messages = listOf(),
                        )
                    )
                }
                _viewState.update { it.copy(chatsList = loadedChats) }
            }
        }
    }

    fun addChat() {
        val newChatName = "Чат №${_viewState.value.chatsList.size}"
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.copy(isLoading = true, isError = false) }
            chatRepository.addChat(newChatName).collect { chats ->
                val loadedChats = mutableListOf<ChatUI>()
                chats.forEach {
                    loadedChats.add(
                        ChatUI(
                            chatId = it.chatId,
                            chatName = it.title,
                            messages = listOf(),
                        )
                    )
                }
                _viewState.update { it.copy(chatsList = loadedChats, isLoading = false, isError = false) }
            }
        }
    }

    fun removeChat(chatId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.removeChat(chatId)
        }
    }
}
