package com.openkin.startaiprog.screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ItemSettingsTextParam(
    placeholder: String,
    isEnabled: Boolean,
    stateValue: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
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
            enabled = isEnabled,
            value = stateValue,
            onValueChange = onValueChanged,
            singleLine = true,
            placeholder = { Text(text = placeholder) },
            textStyle = LocalTextStyle.current
                .copy(
                    textAlign = TextAlign.Left,
                    color = Color.Black,
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
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
