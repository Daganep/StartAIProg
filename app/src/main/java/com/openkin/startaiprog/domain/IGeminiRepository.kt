package com.openkin.startaiprog.domain

import com.openkin.startaiprog.domain.model.ChatMessageUI
import com.openkin.startaiprog.domain.model.ResponseUI
import com.openkin.startaiprog.presentation.screen.settings.SettingsViewState
import kotlinx.coroutines.flow.Flow

interface IGeminiRepository {

    suspend fun askGemini(question: String, chatId: Int)

    fun loadSettings(): Flow<SettingsViewState>

    suspend fun saveSettings(storedSettings: SettingsViewState)
}
