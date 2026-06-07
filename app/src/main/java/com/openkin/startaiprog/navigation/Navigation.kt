package com.openkin.startaiprog.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.openkin.startaiprog.screen.mainscreen.MainScreen
import com.openkin.startaiprog.screen.settings.SettingsScreen
import com.openkin.startaiprog.ui.theme.extraLightGray

@Composable
fun Navigation(scaffoldContentPaddings: PaddingValues) {

    val appRouting by remember { mutableStateOf(AppRouting()) }

    Column(
        modifier = Modifier
            .background(color = extraLightGray)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            Modifier
                .background(Color.Transparent)
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier.fillMaxWidth().weight(1F)
        ) {
            NavDisplay(
                backStack = appRouting.backStack,
                onBack = { appRouting.goBack() },
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
                entryProvider = { key ->
                    when(key) {
                        is Screen.Home ->
                            NavEntry(key = key, content = { MainScreen(appRouting::openSettings) })
                        is Screen.Settings -> {
                            NavEntry(key = key, content = { SettingsScreen(appRouting::home) })
                        }
                    }

                }
            )
        }
        Spacer(
            Modifier
                .background(Color.Transparent)
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .fillMaxWidth()
        )
    }
}
