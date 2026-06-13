package com.openkin.startaiprog.screen.settings.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.screen.mainscreen.model.ThinkingLevel
import com.openkin.startaiprog.ui.theme.blue
import com.openkin.startaiprog.ui.theme.extraLightGray

@Composable
fun ItemSettingsExpand(
    paramName: String,
    paramDescription: String,
    stateValue: ThinkingLevel,
    onValueChanged: (ThinkingLevel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .heightIn(min = 56.dp),
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
            if (paramDescription.isNotEmpty()) {
                Text(
                    text = paramDescription,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
            }
        }
        Box(
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            var isMenuExpanded by remember { mutableStateOf(false) }
            Text(
                text = stateValue.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { isMenuExpanded = !isMenuExpanded }
                    ),
            )
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                containerColor = Color.White,
                border = BorderStroke(width = 1.dp, color = blue),
                modifier = Modifier.padding(0.dp),
            ) {
                ThinkingLevel.entries.forEach { level ->
                    val isSelected = level == stateValue
                    val itemColor = if (isSelected) extraLightGray else Color.White
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = level.name,
                                modifier = Modifier.padding(horizontal = 8.dp),
                            )
                        },
                        onClick = {
                            onValueChanged(level)
                            isMenuExpanded = false
                        },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.background(itemColor),
                    )
                }
            }
        }
    }
}
