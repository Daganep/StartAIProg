package com.openkin.startaiprog.presentation.screen.mainscreen.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.presentation.theme.blue
import com.openkin.startaiprog.presentation.theme.bottomBarTransparent
import com.openkin.startaiprog.presentation.theme.transparent

@Composable
fun NavigationButton(
    isActive: Boolean,
    onClick: () -> Unit,
    @StringRes buttonTextId: Int,
    @DrawableRes iconId: Int,
    @StringRes descriptionId: Int,
    modifier: Modifier = Modifier,
) {
    val contentPadding = if (isActive) 6.dp else 2.dp
    val background = if (isActive) bottomBarTransparent else transparent
    val imageSize = if (isActive) 24.dp else 18.dp
    Button(
        modifier = modifier,
        shape = ShapeDefaults.Medium,
        contentPadding = PaddingValues(contentPadding),
        colors = ButtonColors(
            containerColor = background,
            disabledContainerColor = Color.Gray,
            contentColor = Color.Transparent,
            disabledContentColor = Color.White,
        ),
        onClick = { onClick.invoke() },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = stringResource(descriptionId),
                modifier = Modifier
                    .size(imageSize)
                    //.align(Alignment.CenterVertically),
            )
            Text(
                text = stringResource(buttonTextId),
                color = blue,
                fontSize = 12.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveNavigationButtonPreview() {
    NavigationButton(
        buttonTextId = R.string.navigation_button_chats,
        iconId = R.drawable.chats,
        descriptionId = R.string.navigation_button_chats,
        isActive = true,
        onClick = {},
        modifier = Modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun NonActiveNavigationButtonPreview() {
    NavigationButton(
        buttonTextId = R.string.navigation_button_settings,
        iconId = R.drawable.settings_blue,
        descriptionId = R.string.navigation_button_settings,
        isActive = false,
        onClick = {},
        modifier = Modifier,
    )
}
