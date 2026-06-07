package com.openkin.startaiprog.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.openkin.startaiprog.datastore.INCLUDE_THOUGHTS
import com.openkin.startaiprog.datastore.IS_STOP_SEQUENCES_ENABLED
import com.openkin.startaiprog.datastore.MAX_OUTPUT_TOKENS
import com.openkin.startaiprog.datastore.STOP_SEQUENCES
import com.openkin.startaiprog.datastore.TEMPERATURE
import com.openkin.startaiprog.datastore.THINKING_LEVEL
import com.openkin.startaiprog.datastore.TOP_P
import com.openkin.startaiprog.network.GeminiApi
import com.openkin.startaiprog.network.model.generatetext.*
import com.openkin.startaiprog.screen.mainscreen.model.ResponseUI
import com.openkin.startaiprog.screen.mainscreen.model.ThinkingLevel
import com.openkin.startaiprog.screen.settings.SettingsViewState
import com.openkin.startaiprog.utils.EMPTY_STRING
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_INCLUDE_THOUGHTS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_MAX_TOKENS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TEMPERATURE
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TOP_P
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GeminiRepository(
    private val geminiApi: GeminiApi,
    private val settingsDataStore: DataStore<Preferences>,
) : IGeminiRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun askGemini(question: String) : Flow<ResponseUI> {
        return loadSettings().flatMapLatest  { settings ->
            val stopSequences = if (settings.isStopSequencesEnabled) {
                settings.stopSequences
            } else {
                EMPTY_STRING
            }
            val requestBody = TextGenerateRequest(
                contents = listOf(Parts(parts = listOf(Part(text = question)))),
                generationConfig = GenerationConfig(
                    maxOutputTokens = settings.maxOutputTokens,
                    temperature = settings.temperature,
                    topP = settings.topP,
                    stopSequences = listOf(stopSequences),
                    thinkingConfig = ThinkingConfig(
                        includeThoughts = settings.includeThoughts,
                        thinkingLevel = settings.thinkingLevel.name.lowercase(),
                    ),
                )
            )
            val response = geminiApi.generateText(requestBody)
            var isError = false
            val result = if (response.isSuccess) {
                response.getOrNull()
                    ?.candidates?.get(0)
                    ?.content
                    ?.parts?.get(0)
                    ?.text ?: EMPTY_STRING
            } else {
                isError = true
                "ERROR REQUEST: ${response.exceptionOrNull()?.stackTraceToString()}"
            }
            flowOf(ResponseUI(message = result, isError = isError))
        }
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
