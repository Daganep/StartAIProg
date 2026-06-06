package com.openkin.startaiprog.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.openkin.startaiprog.BuildConfig
import com.openkin.startaiprog.datastore.SettingsDataStore
import com.openkin.startaiprog.network.GeminiApi
import com.openkin.startaiprog.repository.GeminiRepository
import com.openkin.startaiprog.repository.IGeminiRepository
import com.openkin.startaiprog.utils.AppLogger
import com.openkin.startaiprog.utils.IAppLogger
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single<GeminiApi> {
        GeminiApi(
            baseUrl = BuildConfig.GEMINI_BASE_URL,
            apiKey = BuildConfig.GEMINI_API_KEY,
            logger = get(),
        )
    }

    single<IGeminiRepository> {
        GeminiRepository(
            geminiApi = get(),
            settingsDataStore = get()
        )
    }

    single<DataStore<Preferences>>() { androidApplication().SettingsDataStore }

    single<IAppLogger> { AppLogger() }
}
