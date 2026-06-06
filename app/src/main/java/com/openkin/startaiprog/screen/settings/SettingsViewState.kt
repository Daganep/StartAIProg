package com.openkin.startaiprog.screen.settings

data class SettingsViewState(
    val maxOutputTokens: String = "8192",
    val temperature: String = "1.0",
    val topP: String = "0.95",
    val isStopSequencesEnabled: Boolean = false,
    val stopSequences: String = "",
)
