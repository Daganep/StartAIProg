package com.openkin.startaiprog.screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemSettingsParam(
    paramName: String,
    paramDescription: String,
    stateValue: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(5.dp))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(8.dp),
        ) {
            Text(
                text = paramName,
                fontSize = 16.sp,
                lineHeight = 16.sp,
            )
            Text(
                text = paramDescription,
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
            value = stateValue,
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            placeholder = { Text(text = "") },
            textStyle = LocalTextStyle.current
                .copy(
                    textAlign = TextAlign.Right,
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
            ),
        )
    }
}
