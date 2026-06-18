package com.openkin.startaiprog.presentation.screen.chat.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IncomingMessageUI(
    message: String,
    tokensCount: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 42.dp)
                .background(color = Color.White, shape = RoundedCornerShape(5.dp)),
        ) {
            if (tokensCount != 0) {
                Text(
                    text = "токенов: $tokensCount",
                    color = Color.Black,
                    fontSize = 11.sp,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false // Отключает скрытые системные отступы шрифта
                        )
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }
            SelectionContainer {
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun IncomingMessageUIPreview() {
    IncomingMessageUI(
        message = "Ответ на запрос",
        tokensCount = 0,
        modifier = Modifier,
    )
}

@Preview
@Composable
private fun IncomingMessageUIWithCountPreview() {
    IncomingMessageUI(
        message = "Ответ на запрос",
        tokensCount = 583,
        modifier = Modifier,
    )
}
