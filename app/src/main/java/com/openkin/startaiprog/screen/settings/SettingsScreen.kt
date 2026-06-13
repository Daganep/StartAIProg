package com.openkin.startaiprog.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.screen.settings.widgets.ApplySettingsButton
import com.openkin.startaiprog.screen.settings.widgets.ItemSettingsCheckbox
import com.openkin.startaiprog.screen.settings.widgets.ItemSettingsExpand
import com.openkin.startaiprog.screen.settings.widgets.ItemSettingsParam
import com.openkin.startaiprog.screen.settings.widgets.ItemSettingsTextParam
import com.openkin.startaiprog.utils.EMPTY_STRING
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(home: () -> Unit) {
    SettingsScreen(viewModel = koinViewModel(), home = home)
}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    home: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val state by viewModel.viewState.collectAsState()
            // maxOutputTokens
            ItemSettingsParam(
                paramName = "Максимальное количество токенов",
                paramDescription = "Максимальная длина ответа",
                stateValue = state.maxOutputTokens,
                keyboardType = KeyboardType.Number,
                onValueChanged = { newMaximum ->
                    val currentValue = newMaximum.toIntOrNull()
                    if (currentValue != null && currentValue <= 8200) {
                        viewModel.updateMaxOutputTokens(newMaximum)
                    }
                },
            )

            // temperature
            ItemSettingsParam(
                paramName = "Температура",
                paramDescription = "значение от 0.0 до 2.0",
                stateValue = state.temperature,
                keyboardType = KeyboardType.Decimal,
                onValueChanged = { newTemperature ->
                    if (newTemperature.isEmpty()) {
                        viewModel.updateTemperature(newTemperature)
                        return@ItemSettingsParam
                    }
                    val texting: String = newTemperature.replace(',', '.')
                    val currentValue = texting.toFloatOrNull()
                    if (currentValue != null && currentValue in 0F..2F) {
                        viewModel.updateTemperature(currentValue.toString())
                    } else if (texting == "0." || texting == "1.") {
                        viewModel.updateTemperature(currentValue.toString())
                    }
                },
            )

            // topP
            ItemSettingsParam(
                paramName = "Параметр topP",
                paramDescription = "значение от 0.0 до 1.0",
                stateValue = state.topP,
                keyboardType = KeyboardType.Decimal,
                onValueChanged = { newTopP ->
                    if (newTopP.isEmpty()) {
                        viewModel.updateTopP(newTopP)
                        return@ItemSettingsParam
                    }
                    val texting: String = newTopP.replace(',', '.')
                    val currentValue = texting.toFloatOrNull()
                    if (currentValue != null && currentValue in 0F..1F) {
                        viewModel.updateTopP(currentValue.toString())
                    } else if (texting == "0." || texting == "1.") {
                        viewModel.updateTopP(currentValue.toString())
                    }
                },
            )

            // topK
            ItemSettingsParam(
                paramName = "Параметр topK (фиксированный 64)",
                paramDescription = "В модели gemini-3.5-flash константа",
                stateValue = EMPTY_STRING,
                keyboardType = KeyboardType.Unspecified,
            )

            // StopSequences
            ItemSettingsCheckbox(
                paramName = "Использовать параметр StopSequences",
                paramDescription = EMPTY_STRING,
                checkedState = state.isStopSequencesEnabled,
                onCheckedChange = viewModel::updateStopSequencesFlag,
            )
            ItemSettingsTextParam(
                placeholder = "StopSequences",
                isEnabled = state.isStopSequencesEnabled,
                stateValue = state.stopSequences,
                onValueChanged = viewModel::updateStopSequences,
            )

            // Include thoughts
            ItemSettingsCheckbox(
                paramName = "Использовать рассуждения",
                paramDescription = "если включить, попадут в ответ",
                checkedState = state.includeThoughts,
                onCheckedChange = viewModel::updateIncludeThoughtsFlag,
            )

            // Thinking level
            ItemSettingsExpand(
                paramName = "Уровень мышления",
                paramDescription = EMPTY_STRING,
                stateValue = state.thinkingLevel,
                onValueChanged = viewModel::updateThinkingLevel,
            )
            ApplySettingsButton(
                onSaveSettings = viewModel::saveSettings,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadSettings()
    }
}
