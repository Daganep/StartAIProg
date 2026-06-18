package com.openkin.startaiprog.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.openkin.startaiprog.data.datastore.INCLUDE_THOUGHTS
import com.openkin.startaiprog.data.datastore.IS_STOP_SEQUENCES_ENABLED
import com.openkin.startaiprog.data.datastore.MAX_OUTPUT_TOKENS
import com.openkin.startaiprog.data.datastore.SHOW_TOKENS
import com.openkin.startaiprog.data.datastore.STOP_SEQUENCES
import com.openkin.startaiprog.data.datastore.TEMPERATURE
import com.openkin.startaiprog.data.datastore.THINKING_LEVEL
import com.openkin.startaiprog.data.datastore.TOP_P
import com.openkin.startaiprog.domain.ISettingsRepository
import com.openkin.startaiprog.domain.model.ThinkingLevel
import com.openkin.startaiprog.presentation.screen.settings.SettingsViewState
import com.openkin.startaiprog.utils.EMPTY_STRING
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_INCLUDE_THOUGHTS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_MAX_TOKENS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TEMPERATURE
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TOP_P
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val settingsDataStore: DataStore<Preferences>,
) : ISettingsRepository {

    override suspend fun loadChatSettings(): Flow<Boolean> =
        settingsDataStore.data.map { it[SHOW_TOKENS] ?: false }

    override suspend fun updateShowTokenFlag(newValue: Boolean) {
        settingsDataStore.edit { it[SHOW_TOKENS] = newValue }
    }

    override fun loadSettings(): Flow<SettingsViewState> {
        return settingsDataStore.data.map { preferences ->
            val thinkingLevel = ThinkingLevel.entries.firstOrNull() {
                it.name.lowercase() == preferences[THINKING_LEVEL]
            }
            SettingsViewState(
                maxOutputTokens = preferences[MAX_OUTPUT_TOKENS] ?: GEMINI_FLASH_DEFAULT_MAX_TOKENS,
                temperature = preferences[TEMPERATURE] ?: GEMINI_FLASH_DEFAULT_TEMPERATURE,
                topP = preferences[TOP_P] ?: GEMINI_FLASH_DEFAULT_TOP_P,
                isStopSequencesEnabled = preferences[IS_STOP_SEQUENCES_ENABLED] ?: GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON,
                stopSequences = preferences[STOP_SEQUENCES] ?: EMPTY_STRING,
                includeThoughts = preferences[INCLUDE_THOUGHTS] ?: GEMINI_FLASH_DEFAULT_INCLUDE_THOUGHTS,
                thinkingLevel = thinkingLevel ?: ThinkingLevel.MEDIUM,
            )
        }
    }

    override suspend fun saveSettings(storedSettings: SettingsViewState) {
        settingsDataStore.edit { state ->
            state[MAX_OUTPUT_TOKENS] = storedSettings.maxOutputTokens
            state[TEMPERATURE] = storedSettings.temperature
            state[TOP_P] = storedSettings.topP
            state[IS_STOP_SEQUENCES_ENABLED] = storedSettings.isStopSequencesEnabled
            state[STOP_SEQUENCES] = storedSettings.stopSequences
            state[INCLUDE_THOUGHTS] = storedSettings.includeThoughts
            state[THINKING_LEVEL] = storedSettings.thinkingLevel.name.lowercase()
        }
    }
}
