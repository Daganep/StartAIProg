package com.openkin.startaiprog.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.openkin.startaiprog.data.database.ChatsDatabase
import com.openkin.startaiprog.data.datastore.INCLUDE_THOUGHTS
import com.openkin.startaiprog.data.datastore.IS_STOP_SEQUENCES_ENABLED
import com.openkin.startaiprog.data.datastore.MAX_OUTPUT_TOKENS
import com.openkin.startaiprog.data.datastore.STOP_SEQUENCES
import com.openkin.startaiprog.data.datastore.TEMPERATURE
import com.openkin.startaiprog.data.datastore.THINKING_LEVEL
import com.openkin.startaiprog.data.datastore.TOP_P
import com.openkin.startaiprog.data.model.MessageDbo
import com.openkin.startaiprog.data.network.GeminiApi
import com.openkin.startaiprog.data.network.model.generatetext.GenerationConfig
import com.openkin.startaiprog.data.network.model.generatetext.Part
import com.openkin.startaiprog.data.network.model.generatetext.Parts
import com.openkin.startaiprog.data.network.model.generatetext.TextGenerateRequest
import com.openkin.startaiprog.data.network.model.generatetext.ThinkingConfig
import com.openkin.startaiprog.domain.IGeminiRepository
import com.openkin.startaiprog.domain.model.ChatMessageUI
import com.openkin.startaiprog.domain.model.DefaultChat
import com.openkin.startaiprog.domain.model.ResponseUI
import com.openkin.startaiprog.domain.model.ThinkingLevel
import com.openkin.startaiprog.presentation.screen.settings.SettingsViewState
import com.openkin.startaiprog.utils.EMPTY_STRING
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_INCLUDE_THOUGHTS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_MAX_TOKENS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TEMPERATURE
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TOP_P
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class GeminiRepository(
    private val geminiApi: GeminiApi,
    private val settingsDataStore: DataStore<Preferences>,
    private val dataBase: ChatsDatabase,
) : IGeminiRepository {

    override suspend fun askGemini(question: String, chatId: Int) {
        val outgoingMessageDbo = MessageDbo(
            chatId = chatId,
            message = question,
            timestamp = System.currentTimeMillis(),
            outgoing = true,
        )
        dataBase.messagesDao.insert(outgoingMessageDbo)
        loadSettings().zip(dataBase.messagesDao.getChatMessages(chatId)) { settings, messages ->
            val stopSequences = if (settings.isStopSequencesEnabled) {
                settings.stopSequences
            } else {
                EMPTY_STRING
            }
            val parts = messages.map {
                Parts(
                    role = if(it.outgoing) "user" else "model",
                    parts = listOf(Part(text = it.message)),
                )
            }
            val requestBody = TextGenerateRequest(
                contents = parts,
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
            if (response.isSuccess) {
                val message = response.getOrNull()
                    ?.candidates?.get(0)
                    ?.content
                    ?.parts?.get(0)
                    ?.text ?: EMPTY_STRING
                val totalTokensCount = response.getOrNull()?.usageMetadata?.totalTokenCount
                val incomingMessageDbo = MessageDbo(
                    chatId = chatId,
                    message = message,
                    timestamp = System.currentTimeMillis(),
                    outgoing = false,
                )
                dataBase.messagesDao.insert(incomingMessageDbo)
            } else {
                val message = "ERROR REQUEST: ${response.exceptionOrNull()?.stackTraceToString()}"
                val incomingMessageDbo = MessageDbo(
                    chatId = chatId,
                    message = message,
                    timestamp = System.currentTimeMillis(),
                    outgoing = false,
                )
                dataBase.messagesDao.insert(incomingMessageDbo)
            }





//            val stopSequences = if (settings.isStopSequencesEnabled) {
//                settings.stopSequences
//            } else {
//                EMPTY_STRING
//            }
//            val parts = messages.map {
//                Parts(
//                    role = if(it.outgoing) "user" else "model",
//                    parts = listOf(Part(text = it.message)),
//                )
//            }
//            val requestBody = TextGenerateRequest(
//                contents = parts,
//                generationConfig = GenerationConfig(
//                    maxOutputTokens = settings.maxOutputTokens,
//                    temperature = settings.temperature,
//                    topP = settings.topP,
//                    stopSequences = listOf(stopSequences),
//                    thinkingConfig = ThinkingConfig(
//                        includeThoughts = settings.includeThoughts,
//                        thinkingLevel = settings.thinkingLevel.name.lowercase(),
//                    ),
//                )
//            )

//            delay(1500)
//            val message = "Ответ от Gemini: Кек Пек"
//            val incomingMessageDbo = MessageDbo(
//                chatId = chatId,
//                message = message,
//                timestamp = System.currentTimeMillis(),
//                outgoing = false,
//            )
//            dataBase.messagesDao.insert(incomingMessageDbo)
        }.first()
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
