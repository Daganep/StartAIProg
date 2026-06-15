package com.openkin.startaiprog.presentation.screen.chat.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.presentation.theme.blue
import com.openkin.startaiprog.presentation.theme.blueLight

@Composable
fun OutgoingMessageUI(
    message: String,
    tokensCount: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Column (
            modifier = modifier
                .padding(start = 36.dp, end = 8.dp)
                .background(color = blueLight, shape = RoundedCornerShape(5.dp)),
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
private fun OutgoingMessageUIPreview() {
    OutgoingMessageUI(
        message = "Запрос к ИИ",
        tokensCount = 0,
        modifier = Modifier,
    )
}

@Preview
@Composable
private fun OutgoingMessageUIWithTokensPreview() {
    OutgoingMessageUI(
        message = "Запрос к ИИ",
        tokensCount = 76,
        modifier = Modifier,
    )
}
