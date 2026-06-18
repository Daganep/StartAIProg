package com.openkin.startaiprog.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
import com.openkin.startaiprog.domain.model.MessageType
import com.openkin.startaiprog.domain.model.ThinkingLevel
import com.openkin.startaiprog.presentation.screen.settings.SettingsViewState
import com.openkin.startaiprog.utils.EMPTY_STRING
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_INCLUDE_THOUGHTS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_MAX_TOKENS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TEMPERATURE
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TOP_P
import com.openkin.startaiprog.utils.MESSAGES_COUNT_FOR_SUMMARY
import com.openkin.startaiprog.utils.REQUEST_MAKE_SUMMARY
import com.openkin.startaiprog.utils.SUMMARY_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class GeminiRepository(
    private val geminiApi: GeminiApi,
    private val settingsDataStore: DataStore<Preferences>,
    private val dataBase: ChatsDatabase,
) : IGeminiRepository {

    override suspend fun askGemini(question: String, chatId: Int) {
        dataBase.chatsDao.getChatSummary(chatId).zip(loadSettings()) { summary, settings ->
            Pair(summary, settings)
        }.zip(dataBase.messagesDao.getChatMessages(chatId)) { summaryWithSettings, messages ->
            val summary = summaryWithSettings.first
            val settings = summaryWithSettings.second
            val outgoingMessageTimeStamp = System.currentTimeMillis()
            val outgoingMessageDbo = MessageDbo(
                chatId = chatId,
                message = question,
                timestamp = outgoingMessageTimeStamp,
                outgoing = true,
                tokensCount = 0,
                type = MessageType.COMMON.name.lowercase(),
            )
            dataBase.messagesDao.insert(outgoingMessageDbo)

            val stopSequences = if (settings.isStopSequencesEnabled) {
                settings.stopSequences
            } else {
                EMPTY_STRING
            }
            val parts: MutableList<Parts> = mutableListOf()
            if (summary.isNotEmpty()) {
                parts.add(Parts(role = "model", parts = listOf(Part(text = summary))))
            }
            messages.forEach {
                parts.add(
                    Parts(
                        role = if(it.outgoing) "user" else "model",
                        parts = listOf(Part(text = it.message)),
                    )
                )
            }
            parts.add(Parts(role = "user", parts = listOf(Part(text = question))))
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
                val promptTokenCount = response.getOrNull()?.usageMetadata?.promptTokenCount
                val candidatesTokenCount = response.getOrNull()?.usageMetadata?.candidatesTokenCount

                dataBase.chatsDao.updateTotalTokens(
                    chatId = chatId,
                    totalTokens = totalTokensCount ?: 0,
                )

                promptTokenCount?.let { dataBase.messagesDao.updateLastMessageTokens(it) }

                val incomingMessageDbo = MessageDbo(
                    chatId = chatId,
                    message = message,
                    timestamp = System.currentTimeMillis(),
                    outgoing = false,
                    tokensCount = candidatesTokenCount ?: 0,
                    type = MessageType.COMMON.name.lowercase(),
                )
                dataBase.messagesDao.insert(incomingMessageDbo)
            } else {
                val message = "ERROR REQUEST: ${response.exceptionOrNull()?.stackTraceToString()}"
                val incomingMessageDbo = MessageDbo(
                    chatId = chatId,
                    message = message,
                    timestamp = System.currentTimeMillis(),
                    outgoing = false,
                    tokensCount = 0,
                    type = MessageType.WARNING.name.lowercase(),
                )
                dataBase.messagesDao.removeMessage(outgoingMessageTimeStamp)
            }


//            val testMessage = "getOrNull()?.usageMetadata?"
//
//            delay(1500)
//            val totalTokensCount = 200
//            val promptTokenCount = 15
//            val candidatesTokenCount = 80
//
//            dataBase.chatsDao.updateTotalTokens(
//                chatId = chatId,
//                totalTokens = totalTokensCount,
//            )
//
//            val outgoingMessageDbo = MessageDbo(
//                chatId = chatId,
//                message = question,
//                timestamp = System.currentTimeMillis(),
//                outgoing = true,
//                tokensCount = promptTokenCount,
//            )
//            dataBase.messagesDao.insert(outgoingMessageDbo)
//
//            val incomingMessageDbo = MessageDbo(
//                chatId = chatId,
//                message = testMessage,
//                timestamp = System.currentTimeMillis(),
//                outgoing = false,
//                tokensCount = candidatesTokenCount,
//            )
//            dataBase.messagesDao.insert(incomingMessageDbo)

            checkForSummary(chatId, summary, settings, messages)
        }.first()
    }

    private suspend fun checkForSummary(
        chatId: Int,
        summary: String,
        settings: SettingsViewState,
        messages: List<MessageDbo>,
    ) {
        val messagesCount = messages.filter { it.type == MessageType.COMMON.name.lowercase() }.size
        if (messagesCount <= MESSAGES_COUNT_FOR_SUMMARY) return

        val parts: MutableList<Parts> = mutableListOf()
        if (summary.isNotEmpty()) {
            parts.add(Parts(role = "model", parts = listOf(Part(text = summary))))
        }

        val messagesToSummary = messages
            .filter { it.type == MessageType.COMMON.name.lowercase() }
            .take(SUMMARY_SIZE)
        messagesToSummary
            .forEach {
                parts.add(
                    Parts(
                        role = if(it.outgoing) "user" else "model",
                        parts = listOf(Part(text = it.message)),
                    )
                )
            }
        parts.add(Parts(role = "user", parts = listOf(Part(text = REQUEST_MAKE_SUMMARY))))

        val requestBody = TextGenerateRequest(
            contents = parts,
            generationConfig = GenerationConfig(
                maxOutputTokens = settings.maxOutputTokens,
                temperature = settings.temperature,
                topP = settings.topP,
                stopSequences = listOf(EMPTY_STRING),
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
            val candidatesTokenCount = response.getOrNull()?.usageMetadata?.candidatesTokenCount

            dataBase.chatsDao.updateTotalTokens(
                chatId = chatId,
                totalTokens = totalTokensCount ?: 0,
            )

            dataBase.messagesDao.removeMessagesFromFirstTo(messagesToSummary.last().messageId)

            val incomingMessageDbo = MessageDbo(
                chatId = chatId,
                message = "Суммаризация сообщений: $SUMMARY_SIZE",
                timestamp = System.currentTimeMillis(),
                outgoing = false,
                tokensCount = candidatesTokenCount ?: 0,
                type = MessageType.WARNING.name.lowercase(),
            )
            dataBase.messagesDao.insert(incomingMessageDbo)
            dataBase.chatsDao.updateChatSummary(chatId, message)
        }
    }

    private fun loadSettings(): Flow<SettingsViewState> {
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
}
