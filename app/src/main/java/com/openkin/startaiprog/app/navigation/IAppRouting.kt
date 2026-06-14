package com.openkin.startaiprog.app.navigation

interface IAppRouting {

    fun home()

    fun openSettings()

    fun openChat(chatId: Int)

    fun goBack()
}
