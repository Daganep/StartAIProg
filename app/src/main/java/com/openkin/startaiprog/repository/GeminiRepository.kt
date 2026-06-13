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
import com.openkin.startaiprog.network.model.generatetext.GenerationConfig
import com.openkin.startaiprog.network.model.generatetext.Part
import com.openkin.startaiprog.network.model.generatetext.Parts
import com.openkin.startaiprog.network.model.generatetext.TextGenerateRequest
import com.openkin.startaiprog.network.model.generatetext.ThinkingConfig
import com.openkin.startaiprog.screen.chat.model.ChatMessageUI
import com.openkin.startaiprog.screen.chat.model.DefaultChat
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.UUID

class GeminiRepository(
    private val geminiApi: GeminiApi,
    private val settingsDataStore: DataStore<Preferences>,
) : IGeminiRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun askGemini(chatMessage: ChatMessageUI) : Flow<ResponseUI> {
        DefaultChat.addMessage(chatMessage)
        return loadSettings().flatMapLatest  { settings ->
            val stopSequences = if (settings.isStopSequencesEnabled) {
                settings.stopSequences
            } else {
                EMPTY_STRING
            }
            val requestBody = TextGenerateRequest(
                contents = listOf(Parts(parts = listOf(Part(text = chatMessage.message)))),
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
            val newId = UUID.randomUUID().toString()
            val currentTimeMS = System.currentTimeMillis()
            val response = geminiApi.generateText(requestBody)
            val result = if (response.isSuccess) {
                val message = response.getOrNull()
                    ?.candidates?.get(0)
                    ?.content
                    ?.parts?.get(0)
                    ?.text ?: EMPTY_STRING
                val totalTokensCount = response.getOrNull()?.usageMetadata?.totalTokenCount
                val responseChatMessage = ChatMessageUI(newId, message, currentTimeMS, false)
                DefaultChat.addMessage(responseChatMessage)
                ResponseUI(
                    message = responseChatMessage,
                    isError = false,
                    totalTokensCount = totalTokensCount.toString(),
                )
            } else {
                val message = "ERROR REQUEST: ${response.exceptionOrNull()?.stackTraceToString()}"
                val errorChatMessage = ChatMessageUI(newId, message, currentTimeMS, false)
                DefaultChat.addMessage(errorChatMessage)
                ResponseUI(
                    message = errorChatMessage,
                    isError = true,
                    totalTokensCount = EMPTY_STRING,
                )
            }
//            delay(1500)
//            val newId = UUID.randomUUID().toString()
//            val currentTimeMS = System.currentTimeMillis()
//            val message = "Ответ от Gemini:" +
//                    "val result = if (response.isSuccess) {\n" +
//                    "val message = response.getOrNull()\n" +
//                    "response = geminiApi.generateText(requestBody)\n" +
//                    "?.content\n" +
//                    "?.parts?.get(0)\n" +
//                    "?.candidates?.get(0)\n" +
//                    "?.text ?: EMPTY_STRING\n" +
//                    "val totalTokensCount = response.getOrNull()?.usageMetadata?.totalTokenCount\n" +
//                    "ResponseUI(\n" +
//                    "message = message,\n" +
//                    "isError = false,\n" +
//                    "totalTokensCount = totalTokensCount.toString(),\n" +
//                    ")\n" +
//                    "}"
//            val responseMessage = ChatMessageUI(
//                messageId = newId,
//                message = message,
//                messageTime = currentTimeMS,
//                outgoing = false,
//            )
//            val result = ResponseUI(
//                message = responseMessage,
//                isError = false,
//                totalTokensCount = 1250.toString(),
//            )
            flowOf(result)
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
