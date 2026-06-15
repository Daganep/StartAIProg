package com.openkin.startaiprog.presentation.screen.chat.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.presentation.screen.chat.ChatViewState
import com.openkin.startaiprog.presentation.theme.blue
import com.openkin.startaiprog.utils.SINGLE_LINE
import com.openkin.startaiprog.utils.WEIGHT_OF_HALF

@Composable
fun ChatTopAppBar(
    chatState: ChatViewState,
    onShowTokenClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = Color.White)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_go_back_arrow),
                contentDescription = stringResource(R.string.image_description),
                modifier = Modifier
                    .size(36.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { onBackButtonClick() }
                    ),
            )
            Text(
                text = chatState.chatName,
                color = Color.Black,
                maxLines = SINGLE_LINE,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1F).padding(start = 16.dp),
            )
            Row(
                modifier = Modifier.weight(WEIGHT_OF_HALF),
                horizontalArrangement = Arrangement.End,
            ) {
                ChatMenuButton(
                    showTokens = chatState.showTokens,
                    onShowTokenClick = onShowTokenClick,
                    modifier = Modifier,
                )
            }
        }
        if (chatState.showTokens) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(color = blue)
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Всего токенов: ${chatState.totalTokens}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatTopAppBarPreview() {
    ChatTopAppBar(ChatViewState(), {}, {}, Modifier.padding(top = 48.dp))
}
