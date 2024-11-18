package com.example.mvi_clean_room_hilt_flows.presentation.composeExamples

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

/**
 * call by ColorBox(Modifier.fillMaxSize())
 */
@Composable
fun ColorBox(modifier: Modifier){
    // this is internal state because value is set inside of this func and dependent is in the same func
    //remember - remembers last state that was set inside of clickable
   /* val color = remember {
        mutableStateOf(Color.Yellow)
    }*/
    var color by remember {
        mutableStateOf(Color.Yellow)
    }

    Box(modifier = modifier
        .background(color)
        .clickable {
            color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            )
        })
}
