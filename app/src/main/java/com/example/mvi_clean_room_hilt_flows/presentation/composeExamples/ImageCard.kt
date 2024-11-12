package com.example.mvi_clean_room_hilt_flows.presentation.composeExamples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Call it: val painter = painterResource(id = R.drawable.example)
 *                 ImageCard(painter = painter)
 */
@Composable
fun ImageCard(
    painter: Painter,
) {
    Card(
        modifier = Modifier
            .padding(top = 32.dp)
            .height(350.dp)
            .fillMaxWidth(0.5f)
            .background(color = androidx.compose.ui.graphics.Color.LightGray)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
        ) {
            androidx.compose.foundation.Image(
                painter = painter,
                contentDescription = null,
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.BottomStart)
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )))

            Text(
                text = "This is a card",
                color = Color.White,
                style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}
