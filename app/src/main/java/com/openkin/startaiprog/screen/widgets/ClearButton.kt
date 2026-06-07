package com.openkin.startaiprog.screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.utils.EMPTY_STRING

@Composable
fun ClearButton(
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier
            .padding(all = 8.dp)
            .size(32.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(),
        colors = ButtonColors(
            containerColor = Color(0x08000000),
            disabledContainerColor = Color(0x08000000),
            contentColor = Color(0x08000000),
            disabledContentColor = Color(0x08000000),
        ),
        onClick = { onClick(EMPTY_STRING) },
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_clear_text),
            contentDescription = "очистить текст",
            modifier = Modifier
                .size(10.dp)
                .align(Alignment.CenterVertically),
        )
    }
}
