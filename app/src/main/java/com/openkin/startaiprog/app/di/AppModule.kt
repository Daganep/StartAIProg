package com.openkin.startaiprog.app.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.openkin.startaiprog.BuildConfig
import com.openkin.startaiprog.data.database.ChatsDatabase
import com.openkin.startaiprog.data.database.chatsDatabase
import com.openkin.startaiprog.data.datastore.SettingsDataStore
import com.openkin.startaiprog.data.network.GeminiApi
import com.openkin.startaiprog.data.repository.ChatRepository
import com.openkin.startaiprog.data.repository.GeminiRepository
import com.openkin.startaiprog.data.repository.SettingsRepository
import com.openkin.startaiprog.domain.ChatInteractor
import com.openkin.startaiprog.domain.IChatInteractor
import com.openkin.startaiprog.domain.IChatRepository
import com.openkin.startaiprog.domain.IGeminiRepository
import com.openkin.startaiprog.domain.ISettingsRepository
import com.openkin.startaiprog.utils.AppLogger
import com.openkin.startaiprog.utils.IAppLogger
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single<ChatsDatabase> { chatsDatabase(applicationContext = get()) }

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
            settingsDataStore = get(),
            dataBase = get(),
        )
    }

    single<ISettingsRepository> { SettingsRepository(settingsDataStore = get()) }

    single<IChatRepository> { ChatRepository(dataBase = get()) }

    single<IChatInteractor> { ChatInteractor(chatRepository = get()) }

    single<DataStore<Preferences>>() { androidApplication().SettingsDataStore }

    single<IAppLogger> { AppLogger() }
}
