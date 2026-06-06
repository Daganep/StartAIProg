package com.openkin.startaiprog.screen.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.R
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
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(5.dp),
        ) {
            Box(modifier = Modifier) {
                TextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 32.dp)
                        .background(color = Color.White),
                    value = inputText,
                    onValueChange = { newText -> inputText = newText },
                    placeholder = { Text(text = "") },
                    textStyle = LocalTextStyle.current
                        .copy(
                            textAlign = TextAlign.Justify,
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(top = 16.dp, bottom = 16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(5.dp),
        ) {
            Box(modifier = Modifier) {
                Text(
                    text = state,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
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
                onClick = { viewModel.askGemini(inputText) },
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
                    text = "Отправить",
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
