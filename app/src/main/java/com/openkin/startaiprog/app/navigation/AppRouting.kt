package com.openkin.startaiprog.app.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey

sealed class Screen : NavKey {

    data object Home : Screen()

    data object Settings : Screen()

    data class Chat(val chatId: Int) : Screen()
}

class AppRouting : IAppRouting {

    val backStack = mutableStateListOf<Screen>(Screen.Home)

    override fun home() {
        backStack.clear()
        backStack.add(Screen.Home)
    }

    override fun openSettings() {
        backStack.add(Screen.Settings)
    }

    override fun openChat(chatId: Int) {
        backStack.add(Screen.Chat(chatId))
    }

    override fun goBack() {
        backStack.removeLastOrNull()
    }
}
