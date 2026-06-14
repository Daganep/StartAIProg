package com.openkin.startaiprog.presentation.screen.chat.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IncomingMessageUI(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(start = 16.dp, end = 42.dp)
            .background(color = Color.White, shape = RoundedCornerShape(5.dp))
            .padding(all = 8.dp),
    ) {
        SelectionContainer {
            Text(
                text = message,
                fontSize = 14.sp,
                color = Color.Black,
            )
        }
    }
}

@Preview
@Composable
private fun IncomingMessageUIPreview() {
    IncomingMessageUI(
        message = "Ответ на запрос",
        modifier = Modifier,
    )
}
