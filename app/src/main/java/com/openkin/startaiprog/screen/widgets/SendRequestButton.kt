package com.openkin.startaiprog.screen.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.ui.theme.blue

@Composable
fun SendRequestButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier.height(48.dp),
        colors = ButtonColors(
            containerColor = blue,
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            contentColor = Color.White,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
        ),
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            maxLines = 1,
        )
    }
}
