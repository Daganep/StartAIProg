package com.openkin.startaiprog.domain

import com.openkin.startaiprog.presentation.screen.settings.SettingsViewState
import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {

    suspend fun loadChatSettings(): Flow<Boolean>

    suspend fun updateShowTokenFlag(newValue: Boolean)

    fun loadSettings(): Flow<SettingsViewState>

    suspend fun saveSettings(storedSettings: SettingsViewState)
}
