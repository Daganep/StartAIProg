package com.openkin.startaiprog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {

    val state by viewModel.viewState.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            Modifier
                .background(Color.Transparent)
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .fillMaxWidth()
        )
        Card(
            modifier = Modifier.fillMaxWidth().weight(1F).padding(top = 8.dp),
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
            modifier = Modifier.fillMaxWidth().weight(1F).padding(top = 16.dp, bottom = 8.dp),
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
                        .fillMaxSize(),
                )
//                TextField(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(end = 32.dp)
//                        .background(color = Color.White),
//                    value = state,
//                    onValueChange = { newText -> viewModel.askGemini(newText) },
//                    placeholder = { Text(text = "") },
//                    textStyle = LocalTextStyle.current
//                        .copy(
//                            textAlign = TextAlign.Justify,
//                            color = Color.Black,
//                        ),
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = Color.White,
//                        unfocusedContainerColor = Color.White,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        focusedPlaceholderColor = Color.Gray,
//                        unfocusedPlaceholderColor = Color.Gray,
//                        focusedTrailingIconColor = Color.Transparent,
//                        unfocusedTrailingIconColor = Color.Transparent,
//                    ),
//                )
            }
        }
        Button(
            onClick = { viewModel.askGemini(inputText) },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
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
        Spacer(
            Modifier
                .background(Color.Transparent)
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .fillMaxWidth()
        )
    }
}
