package com.example.mvi_clean_room_hilt_flows.presentation.composeExamples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * this is the way to show items, instead of recycleView + adapter
 */
@Composable
fun InsteadRecycleView(){
    /* val scrollState = rememberScrollState()
                     Column(modifier = Modifier.verticalScroll(scrollState)) {
                         for(i in 1..50){
                             Text(text = "Item $i",
                                 fontSize = 24.sp,
                                 textAlign = TextAlign.Center,
                                 modifier = Modifier.fillMaxWidth()
                                     .padding(vertical = 24.dp ))
                         }
                     }*/
    //or better to use, because items inside will be loaded while scrolling
    LazyColumn {
        items(500) {
            Text(text = "Item $it",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp))
        }
    }
}
