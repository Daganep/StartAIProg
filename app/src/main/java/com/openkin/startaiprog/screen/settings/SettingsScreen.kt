package com.openkin.startaiprog.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.screen.widgets.SquareButton
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
            .padding(top = 16.dp),
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
            Row(
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Максимальное количество токенов",
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                    )
                    Text(
                        text = "Максимальная длина ответа",
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
                TextField(
                    modifier = Modifier
                        .width(96.dp)
                        .height(56.dp)
                        .heightIn(min = 0.dp)
                        .padding(0.dp),
                    value = state.maxOutputTokens,
                    onValueChange = { newMaximum ->
                        val currentValue = newMaximum.toIntOrNull()
                        if (currentValue != null && currentValue <= 8200) {
                            viewModel.updateMaxOutputTokens(newMaximum)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    placeholder = { Text(text = "") },
                    textStyle = LocalTextStyle.current
                        .copy(
                            textAlign = TextAlign.Right,
                            color = Color.Black,
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedTrailingIconColor = Color.Transparent,
                        unfocusedTrailingIconColor = Color.Transparent,
                    ),
                )
            }

            // temperature
            Row(
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Температура",
                        fontSize = 16.sp,
                    )
                    Text(
                        text = "значение от 0.0 до 2.0",
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
                TextField(
                    modifier = Modifier
                        .width(96.dp)
                        .height(56.dp)
                        .heightIn(min = 0.dp)
                        .padding(0.dp),
                    value = state.temperature,
                    onValueChange = { newTemperature ->
                        if (newTemperature.isEmpty()) {
                            viewModel.updateTemperature(newTemperature)
                            return@TextField
                        }
                        val texting: String = newTemperature.replace(',', '.')
                        val currentValue = texting.toFloatOrNull()
                        if (currentValue != null && currentValue in 0F..2F) {
                            viewModel.updateTemperature(currentValue.toString())
                        } else if (texting == "0." || texting == "1.") {
                            viewModel.updateTemperature(currentValue.toString())
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    placeholder = { Text(text = "") },
                    textStyle = LocalTextStyle.current
                        .copy(
                            textAlign = TextAlign.Right,
                            color = Color.Black,
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedTrailingIconColor = Color.Transparent,
                        unfocusedTrailingIconColor = Color.Transparent,
                    ),
                )
            }

            // topP
            Row(
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Параметр topP",
                        fontSize = 16.sp,
                    )
                    Text(
                        text = "значение от 0.0 до 1.0",
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
                TextField(
                    modifier = Modifier
                        .width(96.dp)
                        .height(56.dp)
                        .heightIn(min = 0.dp)
                        .padding(0.dp),
                    value = state.topP,
                    onValueChange = { newTopP ->
                        if (newTopP.isEmpty()) {
                            viewModel.updateTopP(newTopP)
                            return@TextField
                        }
                        val texting: String = newTopP.replace(',', '.')
                        val currentValue = texting.toFloatOrNull()
                        if (currentValue != null && currentValue in 0F..1F) {
                            viewModel.updateTopP(currentValue.toString())
                        } else if (texting == "0." || texting == "1.") {
                            viewModel.updateTopP(currentValue.toString())
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    placeholder = { Text(text = "") },
                    textStyle = LocalTextStyle.current
                        .copy(
                            textAlign = TextAlign.Right,
                            color = Color.Black,
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedTrailingIconColor = Color.Transparent,
                        unfocusedTrailingIconColor = Color.Transparent,
                    ),
                )
            }

            // topK
            Row(
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Параметр topK (фиксированный 64)",
                        fontSize = 16.sp,
                        lineHeight = 14.sp,
                    )
                    Text(
                        text = "В модели gemini-3.5-flash константа",
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
            }

            // StopSequences
            Row(
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Использовать параметр",
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                    )
                    Text(
                        text = "StopSequences:",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                    )
                }
                Checkbox(
                    checked = state.isStopSequencesEnabled,
                    onCheckedChange = { checked -> viewModel.updateStopSequencesFlag(checked) }
                )
            }
            Box(
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .heightIn(min = 0.dp)
                        .padding(0.dp),
                    enabled = state.isStopSequencesEnabled,
                    value = state.stopSequences,
                    onValueChange = { newStopSequences ->
                        viewModel.updateStopSequences(newStopSequences)
                    },
                    singleLine = true,
                    placeholder = { Text(text = "StopSequences") },
                    textStyle = LocalTextStyle.current
                        .copy(
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedTrailingIconColor = Color.Transparent,
                        unfocusedTrailingIconColor = Color.Transparent,
                        disabledContainerColor = Color.LightGray,
                        disabledIndicatorColor = Color.LightGray,
                    ),
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = { viewModel.saveSettings() },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.height(48.dp).weight(1F),
                colors = ButtonColors(
                    containerColor = Color.LightGray,
                    disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                    contentColor = Color.Black,
                    disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
                ),
            ) {
                Text(
                    text = "Применить",
                    lineHeight = 12.sp,
                    maxLines = 1,
                )
            }
            SquareButton(
                openSettings = { home.invoke() },
                icon = R.drawable.arrow,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(48.dp)
                    .width(48.dp),
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadSettings()
    }
}
