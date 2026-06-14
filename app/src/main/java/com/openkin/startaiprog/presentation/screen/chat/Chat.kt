package com.openkin.startaiprog.presentation.screen.chat

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.app.navigation.IAppRouting
import com.openkin.startaiprog.presentation.screen.mainscreen.widgets.SendTextField
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.LaunchedEffect
import com.openkin.startaiprog.presentation.screen.chat.widgets.ChatTopAppBar
import com.openkin.startaiprog.presentation.screen.chat.widgets.IncomingMessageUI
import com.openkin.startaiprog.presentation.screen.chat.widgets.OutgoingMessageUI

@Composable
fun Chat(
    chatId: Int,
    routing: IAppRouting,
    scaffoldContentPaddings: PaddingValues,
) {
    Chat(
        chatId = chatId,
        viewModel = koinViewModel(),
        routing = routing,
        scaffoldContentPaddings = scaffoldContentPaddings,
    )
}

@Composable
fun Chat(
    chatId: Int,
    viewModel: ChatViewModel,
    routing: IAppRouting,
    scaffoldContentPaddings: PaddingValues,
) {
    val state by viewModel.viewState.collectAsState()
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(scaffoldContentPaddings)
            .imePadding(),
    ) {
        ChatTopAppBar(
            chatName = state.chatId,
            onMenuClick = {},
        )
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(vertical = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Bottom,
            ),
        ) {
            items(items = state.messages, key = { it.messageId }) { item ->
                if (item.outgoing) {
                    OutgoingMessageUI(message = item.message, Modifier.align(Alignment.End))
                } else {
                    IncomingMessageUI(message = item.message)
                }
            }
        }
        SendTextField(
            promtText = state.promt,
            onInputTextChanged = viewModel::updatePromt,
            onSendClick = viewModel::askGemini,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
    LaunchedEffect(Unit) {
        viewModel.loadChat(chatId)
    }
    LaunchedEffect(key1 = state.messages.size) {
        if (state.messages.isNotEmpty()) {
            lazyListState.animateScrollToItem(index = state.messages.size - 1)
        }
    }
}
