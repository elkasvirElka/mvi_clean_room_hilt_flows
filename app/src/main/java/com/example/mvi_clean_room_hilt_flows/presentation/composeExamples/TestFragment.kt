package com.example.mvi_clean_room_hilt_flows.presentation.composeExamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class TestFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate a ComposeView and set the content to your composable
        return ComposeView(requireContext()).apply {
            setContent {
                Text("lalala")
               // MovieListScreen() // Call your composable here
            }
        }
    }

}
