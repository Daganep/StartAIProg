package com.openkin.startaiprog.di

import com.openkin.startaiprog.screen.mainscreen.MainViewModel
import com.openkin.startaiprog.screen.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel(geminiRepository = get()) }

    viewModel { SettingsViewModel(geminiRepository = get()) }
}
