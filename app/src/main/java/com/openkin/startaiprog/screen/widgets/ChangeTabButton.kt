package com.openkin.startaiprog.screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.ui.theme.halfTransparent
import com.openkin.startaiprog.ui.theme.transparent

@Composable
fun ChangeTabButton(
    onClick: () -> Unit,
    leftToRight: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(
        shape = CircleShape,
        contentPadding = PaddingValues(8.dp),
        colors = ButtonColors(
            containerColor = halfTransparent,
            disabledContainerColor = transparent,
            contentColor = transparent,
            disabledContentColor = transparent,
        ),
        onClick = { onClick() },
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_go_back_arrow),
            contentDescription = "Переключиться на другую вкладку",
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterVertically)
                .graphicsLayer { scaleX = if (!leftToRight) 1F else -1F },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeTabButtonPreview() {
    ChangeTabButton(onClick = {}, true, modifier = Modifier)
}
