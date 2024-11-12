package com.example.mvi_clean_room_hilt_flows.presentation.composeExamples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvi_clean_room_hilt_flows.FragmentContainer


@Composable
fun rowAndColumnExample(){
    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize(0.7f).background(color = androidx.compose.ui.graphics.Color.LightGray)) {
        Text(
            text = "Hello Android!")
        Text(
            text = "Hello Android!")
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth().height(100.dp).padding(16.dp)
            .border(width = 1.dp, color = androidx.compose.ui.graphics.Color.Red)
            .background(color = androidx.compose.ui.graphics.Color.Cyan)) {
            Text(
                text = "Hello Rows!")
            Text(
                text = "Hello Rows!")
        }
        FragmentContainer()
    }
}
