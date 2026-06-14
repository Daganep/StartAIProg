package com.openkin.startaiprog.presentation.screen.mainscreen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.app.navigation.AppRouting
import com.openkin.startaiprog.app.navigation.IAppRouting
import com.openkin.startaiprog.app.navigation.Screen

@Composable
fun NavigationBar(
    routing: IAppRouting,
    activeScreen: Screen,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(all = 24.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            NavigationButton(
                modifier = Modifier,
                iconId = R.drawable.chats,
                descriptionId = R.string.navigation_button_chats,
                isActive = activeScreen == Screen.Home,
                onClick = routing::home,
                buttonTextId = R.string.navigation_button_chats,
            )
            NavigationButton(
                modifier = Modifier,
                iconId = R.drawable.settings_blue,
                descriptionId = R.string.navigation_button_settings,
                isActive = activeScreen == Screen.Settings,
                onClick = routing::openSettings,
                buttonTextId = R.string.navigation_button_settings,
            )
        }
    }
}

@Preview
@Composable
private fun NavigationBarPreview() {
    NavigationBar(AppRouting(), Screen.Home, Modifier)
}
