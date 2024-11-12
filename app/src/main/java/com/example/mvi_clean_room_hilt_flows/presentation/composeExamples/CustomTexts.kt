package com.example.mvi_clean_room_hilt_flows.presentation.composeExamples

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.mvi_clean_room_hilt_flows.R


@Composable
fun StyleText(){

    var fontFamily = FontFamily(
        Font(R.font.edu_regular),
        Font(R.font.edu_medium, FontWeight.Medium),
        Font(R.font.edu_bold, FontWeight.Bold)
    )
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 24.sp, color = Color.Green)) {
                append("Hello")
            }
            append(" World")
        },
        fontSize = 24.sp,
        color = Color.White,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center    )
}
