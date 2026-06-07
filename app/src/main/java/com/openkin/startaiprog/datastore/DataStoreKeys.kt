package com.openkin.startaiprog.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.openkin.startaiprog.utils.START_AI_PREFS

val Context.SettingsDataStore: DataStore<Preferences> by preferencesDataStore(START_AI_PREFS)

val MAX_OUTPUT_TOKENS = stringPreferencesKey("MAX_OUTPUT_TOKENS")
val TEMPERATURE = stringPreferencesKey("TEMPERATURE")
val TOP_P = stringPreferencesKey("TOP_P")
val IS_STOP_SEQUENCES_ENABLED = booleanPreferencesKey("IS_STOP_SEQUENCES_ENABLED")
val STOP_SEQUENCES = stringPreferencesKey("STOP_SEQUENCES")
val INCLUDE_THOUGHTS = booleanPreferencesKey("INCLUDE_THOUGHTS")
val THINKING_LEVEL = stringPreferencesKey("THINKING_LEVEL")
