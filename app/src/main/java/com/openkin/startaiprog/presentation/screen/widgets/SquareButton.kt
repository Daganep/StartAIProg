package com.openkin.startaiprog.presentation.screen.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.presentation.theme.blue

@Composable
fun SquareButton(
    openSettings: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .background(color = blue, shape = RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Настройки",
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { openSettings.invoke() }
                ),
        )
    }
}
