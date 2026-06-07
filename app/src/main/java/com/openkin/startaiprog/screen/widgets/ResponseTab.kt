package com.openkin.startaiprog.screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.screen.mainscreen.MainViewState
import com.openkin.startaiprog.ui.theme.blue

@Composable
fun ResponseTab(
    state: MainViewState,
    onChangeTabClick: () -> Unit,
    timerValue: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            contentAlignment = Alignment.Center,
        ) {
            if (!state.isLoading) {
                if (!state.isError) {
                    SelectionContainer {
                        Text(
                            text = state.response,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                                .verticalScroll(rememberScrollState()),
                        )
                    }
                } else {
                    Text(
                        text = state.response,
                        color = Color.Red,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.heightIn(max = 400.dp).widthIn(max = 300.dp),
                    )
                }
                ChangeTabButton(
                    onClick = onChangeTabClick,
                    leftToRight = false,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterStart),
                )
            } else {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(color = blue)
                .padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ResponseTimer(timerValue = timerValue, modifier = Modifier.padding(end = 8.dp))
            if (state.totalTokens.isNotEmpty()) {
                Spacer(
                    Modifier
                        .background(Color.White)
                        .width(2.dp)
                        .fillMaxHeight(),
                )
                Text(
                    text = "Total tokens: ${state.totalTokens}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
