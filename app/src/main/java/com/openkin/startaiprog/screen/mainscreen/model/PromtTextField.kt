package com.openkin.startaiprog.screen.mainscreen.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.startaiprog.R
import com.openkin.startaiprog.utils.EMPTY_STRING

@Composable
fun SendTextField(
    promtText: String,
    onInputTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isKeyboardVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val bottomPadding = if (isKeyboardVisible) 64.dp else 8.dp
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, bottom = bottomPadding),
        verticalAlignment = Alignment.Bottom,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 4.dp, vertical = 2.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
                    .heightIn(min = 24.dp),
            ) {
                if (promtText.isEmpty()) {
                    Text(
                        text = stringResource(R.string.promt_placeholder),
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
                    )
                }
                BasicTextField(
                    value = promtText,
                    onValueChange = onInputTextChanged,
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .padding(bottom = 2.dp)
        ) {
//            if (promtText.isNotBlank()) {
                FilledIconButton(
                    onClick = {
                        onSendClick()
                        onInputTextChanged("") // Очищаем поле
                    },
                    modifier = Modifier.size(44.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(0xFF5288c1) // Фирменный голубой цвет Telegram
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_lightning_white),
                        contentDescription = stringResource(R.string.image_description),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
//            }
//            else {
//                FilledIconButton(
//                    onClick = { /* Запись голоса */ },
//                    modifier = Modifier.size(44.dp),
//                    colors = IconButtonDefaults.filledIconButtonColors(
//                        containerColor = Color(0xFF5288c1)
//                    )
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_mic),
//                        contentDescription = stringResource(R.string.image_description),
//                        tint = Color.White,
//                        modifier = Modifier.size(22.dp)
//                    )
//                }
//            }
        }
    }
}

@Preview
@Composable
private fun SendTextFieldPreview() {
    SendTextField(
        promtText = EMPTY_STRING,
        onInputTextChanged = {},
        onSendClick = {},
        modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
    )
}
