package com.openkin.startaiprog.di

import com.openkin.startaiprog.BuildConfig
import com.openkin.startaiprog.repository.GeminiRepository
import com.openkin.startaiprog.repository.IGeminiRepository
import com.openkin.startaiprog.network.GeminiApi
import com.openkin.startaiprog.utils.AppLogger
import com.openkin.startaiprog.utils.IAppLogger
import org.koin.dsl.module

val appModule = module {

    single<GeminiApi> {
        GeminiApi(
            baseUrl = BuildConfig.GEMINI_BASE_URL,
            apiKey = BuildConfig.GEMINI_API_KEY,
            logger = get(),
        )
    }

    single<IGeminiRepository> { GeminiRepository(geminiApi = get()) }

    single<IAppLogger> { AppLogger() }
}
