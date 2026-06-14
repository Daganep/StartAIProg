package com.openkin.startaiprog.presentation.screen.chat.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.utils.SINGLE_LINE
import com.openkin.startaiprog.utils.WEIGHT_OF_HALF

@Composable
fun ChatTopAppBar(
    chatName: String,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = chatName,
            maxLines = SINGLE_LINE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1F),
        )
        Row(
            modifier = Modifier.weight(WEIGHT_OF_HALF),
            horizontalArrangement = Arrangement.End,
        ) {
            ChatMenuButton(
                modifier = Modifier,
                onMenuClick = onMenuClick,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatTopAppBarPreview() {
    ChatTopAppBar("Чат №1", Modifier, {})
}
