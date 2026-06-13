package com.openkin.startaiprog.screen.chat.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.ui.theme.blue

@Composable
fun OutgoingMessageUI(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Box(
            modifier = modifier
                .padding(start = 36.dp, end = 8.dp)
                .background(color = blue, shape = RoundedCornerShape(5.dp))
                .padding(all = 8.dp),
        ) {
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
private fun OutgoingMessageUIPreview() {
    OutgoingMessageUI(
        message = "Запрос к ИИ",
        modifier = Modifier,
    )
}
