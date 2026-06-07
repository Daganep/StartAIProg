package com.openkin.startaiprog.repository

import com.openkin.startaiprog.screen.mainscreen.model.ResponseUI
import com.openkin.startaiprog.screen.settings.SettingsViewState
import kotlinx.coroutines.flow.Flow

interface IGeminiRepository {

    suspend fun askGemini(question: String): Flow<ResponseUI>

    fun loadSettings(): Flow<SettingsViewState>

    suspend fun saveSettings(storedSettings: SettingsViewState)
}
