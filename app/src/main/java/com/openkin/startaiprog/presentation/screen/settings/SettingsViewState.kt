package com.openkin.startaiprog.presentation.screen.settings

import com.openkin.startaiprog.domain.model.ThinkingLevel
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_INCLUDE_THOUGHTS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_MAX_TOKENS
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TEMPERATURE
import com.openkin.startaiprog.utils.GEMINI_FLASH_DEFAULT_TOP_P

data class SettingsViewState(
    val maxOutputTokens: String = GEMINI_FLASH_DEFAULT_MAX_TOKENS,
    val temperature: String = GEMINI_FLASH_DEFAULT_TEMPERATURE,
    val topP: String = GEMINI_FLASH_DEFAULT_TOP_P,
    val isStopSequencesEnabled: Boolean = GEMINI_FLASH_DEFAULT_IS_STOP_SEQ_ON,
    val stopSequences: String = "",
    val includeThoughts: Boolean = GEMINI_FLASH_DEFAULT_INCLUDE_THOUGHTS,
    val thinkingLevel: ThinkingLevel = ThinkingLevel.MEDIUM,
)
