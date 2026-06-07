package com.openkin.startaiprog.screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.openkin.startaiprog.screen.mainscreen.MainViewState

@Composable
fun PromtTab(
    state: MainViewState,
    updatePromt: (String) -> Unit,
    onChangeTabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center,
        ) {
            if (state.showPromt) {
                TextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 32.dp)
                        .background(color = Color.White),
                    value = state.promt,
                    onValueChange = { newText -> updatePromt(newText) },
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
                    ),
                )
                if (state.response.isNotEmpty()) {
                    ChangeTabButton(
                        onClick = onChangeTabClick,
                        leftToRight = true,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterEnd),
                    )
                }
                if (state.promt.isNotEmpty()) {
                    ClearButton(
                        onClick = updatePromt,
                        modifier = Modifier.align(Alignment.TopEnd),
                    )
                }
            }
        }
    }
}
