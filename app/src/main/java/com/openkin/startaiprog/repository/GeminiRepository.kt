package com.openkin.startaiprog.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.openkin.startaiprog.datastore.IS_STOP_SEQUENCES_ENABLED
import com.openkin.startaiprog.datastore.MAX_OUTPUT_TOKENS
import com.openkin.startaiprog.datastore.STOP_SEQUENCES
import com.openkin.startaiprog.datastore.TEMPERATURE
import com.openkin.startaiprog.datastore.TOP_P
import com.openkin.startaiprog.network.GeminiApi
import com.openkin.startaiprog.network.model.generatetext.GenerationConfig
import com.openkin.startaiprog.network.model.generatetext.Part
import com.openkin.startaiprog.network.model.generatetext.Parts
import com.openkin.startaiprog.network.model.generatetext.TextGenerateRequest
import com.openkin.startaiprog.screen.settings.SettingsViewState
import com.openkin.startaiprog.utils.EMPTY_STRING
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_MAX_TOKENS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TEMPERATURE
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TOP_P
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GeminiRepository(
    private val geminiApi: GeminiApi,
    private val settingsDataStore: DataStore<Preferences>,
) : IGeminiRepository {

//    override suspend fun askGemini(question: String) : Flow<String> {
//        val response = geminiApi.simpleRequest(
//            RequestPromt(model = MODEL_GEMINI_FLASH, input = question)
//        )
//        val result = if (response.isSuccess) {
//            response.getOrNull()
//                ?.steps
//                ?.firstOrNull { it.type == "model_output" }
//                ?.content
//                ?.get(0)
//                ?.text
//                ?: "empty"
//        } else {
//            Log.e("MyFilter", "Ошибка из-за: ${response.exceptionOrNull()?.stackTraceToString()}")
//            "ERROR REQUEST блин"
//        }
//        return flowOf(result)
//    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun askGemini(question: String) : Flow<String> {
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
                )
            )
            val response = geminiApi.generateText(requestBody)
            val result = if (response.isSuccess) {
                response.getOrNull()
                    ?.candidates?.get(0)
                    ?.content
                    ?.parts?.get(0)
                    ?.text ?: "Empty"
            } else {
                Log.e("MyFilter", "Ошибка из-за: ${response.exceptionOrNull()?.stackTraceToString()}")
                "ERROR REQUEST блин"
            }
            flowOf(result)
        }
    }

    override fun loadSettings(): Flow<SettingsViewState> {
        return settingsDataStore.data.map { preferences ->
            SettingsViewState(
                maxOutputTokens = preferences[MAX_OUTPUT_TOKENS] ?: GEMINI_FLASH_DEFAULT_MAX_TOKENS,
                temperature = preferences[TEMPERATURE] ?: GEMINI_FLASH_DEFAULT_TEMPERATURE,
                topP = preferences[TOP_P] ?: GEMINI_FLASH_DEFAULT_TOP_P,
                isStopSequencesEnabled = preferences[IS_STOP_SEQUENCES_ENABLED] ?: GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON,
                stopSequences = preferences[STOP_SEQUENCES] ?: EMPTY_STRING
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
        }
    }
}
