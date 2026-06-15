package com.openkin.startaiprog.presentation.screen.chat.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.domain.model.ThinkingLevel
import com.openkin.startaiprog.presentation.theme.blue
import com.openkin.startaiprog.presentation.theme.extraLightGray

@Composable
fun ChatMenuButton(
    showTokens: Boolean,
    onShowTokenClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
//    var currentSortType by remember { mutableStateOf(SortType.CREATE_DATE) }
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_menu_views),
            contentDescription = stringResource(R.string.image_description),
            modifier = modifier
                .padding(start = 24.dp, bottom = 8.dp)
                .size(28.dp)
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
            //ThinkingLevel.entries.forEach { level ->
                //val isSelected = level == stateValue
                //val itemColor = if (isSelected) extraLightGray else Color.White
                val text = if (showTokens) {
                    stringResource(R.string.chat_menu_do_not_show_tokens)
                } else {
                    stringResource(R.string.chat_menu_show_tokens)
                }
                DropdownMenuItem(
                    text = {
                        Text(
                            text = text,
                            modifier = Modifier.padding(horizontal = 8.dp),
                        )
                    },
                    onClick = {
                        onShowTokenClick()
                        isMenuExpanded = false
                    },
                    contentPadding = PaddingValues(0.dp),
                    //modifier = Modifier.background(itemColor),
                )
            //}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatMenuButtonPreview() {
    ChatMenuButton(showTokens = false, onShowTokenClick = {}, modifier = Modifier)
}
