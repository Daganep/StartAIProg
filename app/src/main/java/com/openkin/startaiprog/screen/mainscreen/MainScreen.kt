package com.openkin.startaiprog.screen.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.screen.widgets.PromtTab
import com.openkin.startaiprog.screen.widgets.ResponseTab
import com.openkin.startaiprog.screen.widgets.SendRequestButton
import com.openkin.startaiprog.screen.widgets.SquareButton
import com.openkin.startaiprog.screen.widgets.TabAnimation
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun MainScreen(openSettings: () -> Unit) {
    MainScreen(viewModel = koinViewModel(), openSettings = openSettings)
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    openSettings: () -> Unit,
) {
    val state by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(top = 16.dp),
        ) {
            PromtTab(
                state = state,
                updatePromt = viewModel::updatePromt,
                onChangeTabClick = viewModel::changeTab,
                modifier = Modifier.weight(1F),
            )
            TabAnimation(isTabShown = !state.showPromt) {
                val formattedTime by remember(state.timerValue) {
                    derivedStateOf {
                        val seconds = state.timerValue / 1000f
                        String.format(Locale.US, "%.2f", seconds)
                    }
                }
                ResponseTab(
                    state = state,
                    onChangeTabClick = viewModel::changeTab,
                    timerValue = formattedTime,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SendRequestButton(
                text = if (state.showPromt) "Отправить" else "К промту",
                onClick = if (state.showPromt) viewModel::askGemini else viewModel::changeTab,
                modifier = Modifier.weight(1F),
            )
            SquareButton(
                openSettings = openSettings,
                icon = R.drawable.settings_white,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(48.dp)
                    .width(48.dp),
            )
        }
    }
}
