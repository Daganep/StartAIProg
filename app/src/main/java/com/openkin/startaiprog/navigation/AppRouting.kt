package com.openkin.startaiprog.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey

sealed class Screen : NavKey {

    data object Home : Screen()

    data object Settings : Screen()
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

    override fun goBack() {
        backStack.removeLastOrNull()
    }
}
