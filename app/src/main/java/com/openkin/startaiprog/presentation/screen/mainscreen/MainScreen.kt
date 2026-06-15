package com.openkin.startaiprog.presentation.screen.mainscreen

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.openkin.startaiprog.R
import com.openkin.startaiprog.app.navigation.AppRouting
import com.openkin.startaiprog.presentation.screen.mainscreen.widgets.SwipeableChat
import com.openkin.startaiprog.presentation.screen.mainscreen.widgets.ActionOnSwipe
import com.openkin.startaiprog.presentation.screen.mainscreen.widgets.ItemChat
import com.openkin.startaiprog.presentation.screen.mainscreen.widgets.AddChatButton

@Composable
fun MainScreen(
    routing: AppRouting,
    scaffoldContentPaddings: PaddingValues,
) {
    MainScreen(viewModel = koinViewModel(), routing = routing, scaffoldContentPaddings)
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    routing: AppRouting,
    scaffoldContentPaddings: PaddingValues,
) {
    val state by viewModel.viewState.collectAsState()

    // Первое число - id заметки, второе - позиция в списке заметок
    var swipedChat by remember { mutableStateOf(Pair(0, -1)) }
    val lazyListState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn (
            state = lazyListState,
            contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
            modifier = Modifier.padding(vertical = 8.dp),
        ) {
            itemsIndexed(items = state.chatsList, key = { _, item -> item.chatId }) { index, item ->
                SwipeableChat(
                    isRevealed = item.chatId == swipedChat.first,
                    actions = {
                        ActionOnSwipe(
                            onClick = {
                                swipedChat = Pair(0, -1)
                                viewModel.removeChat(item.chatId)
                            },
                            drawableId = R.drawable.image_item_to_bin,
                            contentDescriptionId = R.string.image_description,
                            modifier = Modifier,
                        )
                    },
                    onExpanded = { swipedChat = Pair(item.chatId, index) },
                    onCollapsed = { swipedChat = Pair(0, -1) },
                    modifier = Modifier.animateItem(),
                ) {
                    ItemChat(
                        chat = item,
                        onChatClick = routing::openChat,
                        modifier = Modifier,
                    )
                }
            }
        }
        AddChatButton(
            onClick = viewModel::addChat,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 104.dp),
        )
    }
    LaunchedEffect(Unit) {
        viewModel.loadChats()
    }
}
