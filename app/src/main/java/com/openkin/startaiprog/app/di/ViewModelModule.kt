package com.openkin.startaiprog.app.di

import com.openkin.startaiprog.presentation.screen.chat.ChatViewModel
import com.openkin.startaiprog.presentation.screen.mainscreen.MainViewModel
import com.openkin.startaiprog.presentation.screen.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(chatRepository = get())
    }

    viewModel {
        ChatViewModel(
            geminiRepository = get(),
            chatInteractor = get(),
            settingsRepository = get(),
        )
    }

    viewModel { SettingsViewModel(settingsRepository = get()) }
}
