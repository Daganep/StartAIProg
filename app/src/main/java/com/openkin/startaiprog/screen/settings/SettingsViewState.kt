package com.openkin.startaiprog.screen.settings

import com.openkin.startaiprog.screen.mainscreen.model.ThinkingLevel

data class SettingsViewState(
    val maxOutputTokens: String = "8192",
    val temperature: String = "1.0",
    val topP: String = "0.95",
    val isStopSequencesEnabled: Boolean = false,
    val stopSequences: String = "",
    val includeThoughts: Boolean = false,
    val thinkingLevel: ThinkingLevel = ThinkingLevel.MEDIUM,
)
