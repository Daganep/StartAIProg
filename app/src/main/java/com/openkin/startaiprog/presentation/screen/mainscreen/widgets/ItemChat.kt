package com.openkin.startaiprog.presentation.screen.mainscreen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.openkin.startaiprog.domain.model.ChatUI
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.utils.SINGLE_LINE

@Composable
fun ItemChat(
    chat: ChatUI,
    onChatClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onChatClick(chat.chatId) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.archives),
            contentDescription = stringResource(R.string.image_description),
            modifier = Modifier
                .size(48.dp)
                .padding(start = 16.dp)
            ,
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = chat.chatName,
                maxLines = SINGLE_LINE,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemChatPreview() {
    val chat = ChatUI(
        chatId = 15,
        chatName = "Чат по умолчанию",
        messages = listOf(),
    )
    ItemChat(
        chat = chat,
        onChatClick = {},
        modifier = Modifier,
    )
}
