package com.openkin.startaiprog.screen.mainscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.openkin.startaiprog.navigation.AppRouting
import com.openkin.startaiprog.screen.mainscreen.model.SwipeableChat
import com.openkin.startaiprog.screen.mainscreen.model.ActionOnSwipe
import com.openkin.startaiprog.screen.mainscreen.model.ItemChat

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
    var swipedChat by remember { mutableStateOf(Pair("0", -1)) }
    val lazyListState = rememberLazyListState()

    LazyColumn (
        state = lazyListState,
        contentPadding = PaddingValues(bottom = 8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
    ) {
        itemsIndexed(items = state.chatsList, key = { _, item -> item.chatId }) { index, item ->
            SwipeableChat(
                isRevealed = item.chatId == swipedChat.first,
                actions = {
                    ActionOnSwipe(
                        onClick = {
                            swipedChat = Pair("0", -1)
                            viewModel.sendChatToArchive(item.chatId)
                        },
                        drawableId = R.drawable.image_put_to_archive,
                        contentDescriptionId = R.string.image_description,
                        modifier = Modifier,
                    )
                },
                onExpanded = { swipedChat = Pair(item.chatId, index) },
                onCollapsed = { swipedChat = Pair("0", -1) },
                modifier = Modifier.animateItem(),
            ) {
                ItemChat(
                    chat = item,
                    onChatClick = routing::openChat,
                    modifier = Modifier,
                )
            }
        }
//        itemsIndexed(items = state.chatsList, key = { _, item -> item.chatId }) { -> index, item
//
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1F)
//                .padding(top = 16.dp),
//        ) {
//            PromtTab(
//                state = state,
//                updatePromt = viewModel::updatePromt,
//                onChangeTabClick = viewModel::changeTab,
//                modifier = Modifier.weight(1F),
//            )
//            TabAnimation(isTabShown = !state.showPromt) {
//                val formattedTime by remember(state.timerValue) {
//                    derivedStateOf {
//                        val seconds = state.timerValue / 1000f
//                        String.format(Locale.US, "%.2f", seconds)
//                    }
//                }
//                ResponseTab(
//                    state = state,
//                    onChangeTabClick = viewModel::changeTab,
//                    timerValue = formattedTime,
//                )
//            }
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(all = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            SendRequestButton(
//                text = if (state.showPromt) "Отправить" else "К промту",
//                onClick = if (state.showPromt) viewModel::askGemini else viewModel::changeTab,
//                modifier = Modifier.weight(1F),
//            )
//            SquareButton(
//                openSettings = openSettings,
//                icon = R.drawable.settings_white,
//                modifier = Modifier
//                    .padding(start = 8.dp)
//                    .height(48.dp)
//                    .width(48.dp),
//            )
//        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadChats()
    }
}
