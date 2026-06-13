package com.openkin.startaiprog.navigation

interface IAppRouting {

    fun home()

    fun openSettings()

    fun openChat(chatId: String)

    fun goBack()
}
