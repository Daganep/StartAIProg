package com.openkin.startaiprog.screen.settings.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemSettingsCheckbox(
    paramName: String,
    paramDescription: String,
    checkedState: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
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
        Checkbox(
            checked = checkedState,
            onCheckedChange = onCheckedChange
        )
    }
}
