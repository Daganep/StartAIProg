package com.openkin.startaiprog.screen.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.screen.widgets.ChangeTabButton
import com.openkin.startaiprog.screen.widgets.ClearButton
import com.openkin.startaiprog.screen.widgets.SquareButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(openSettings: () -> Unit) {
    MainScreen(viewModel = koinViewModel(), openSettings = openSettings)
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    openSettings: () -> Unit,
) {
    val state by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(top = 16.dp),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
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
                            onValueChange = { newText -> viewModel.updatePromt(newText) },
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
                                onClick = viewModel::changeTab,
                                leftToRight = true,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .align(Alignment.CenterEnd),
                            )
                        }
                        if (state.promt.isNotEmpty()) {
                            ClearButton(
                                onClick = viewModel::updatePromt,
                                modifier = Modifier.align(Alignment.TopEnd),
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = !state.showPromt,
                enter = expandHorizontally(
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearOutSlowInEasing,
                    )
                ),
                exit = shrinkHorizontally(
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearOutSlowInEasing,
                    )
                ),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(5.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
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
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            ChangeTabButton(
                                onClick = viewModel::changeTab,
                                leftToRight = false,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .align(Alignment.CenterStart),
                            )
                        } else {
                            CircularProgressIndicator(modifier = Modifier.size(50.dp))
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    if (state.showPromt) {
                        viewModel.askGemini()
                    } else {
                        viewModel.changeTab()
                    }
                },
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
                    text = if (state.showPromt) "Отправить" else "К промту",
                    lineHeight = 12.sp,
                    maxLines = 1,
                )
            }
            SquareButton(
                openSettings = openSettings,
                icon = R.drawable.settings,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(48.dp)
                    .width(48.dp),
            )
        }
    }
}
