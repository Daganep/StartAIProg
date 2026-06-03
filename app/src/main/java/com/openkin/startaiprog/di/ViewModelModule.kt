package com.openkin.startaiprog.di

import com.openkin.startaiprog.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel(geminiRepository = get()) }
}
