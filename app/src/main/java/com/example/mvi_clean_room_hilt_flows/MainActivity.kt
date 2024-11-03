package com.example.mvi_clean_room_hilt_flows

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.example.mvi_clean_room_hilt_flows.presentation.MovieListFragment
import com.example.mvi_clean_room_hilt_flows.ui.theme.Mvi_clean_room_hilt_flowsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // activityRetainScope() is score of Activities viewModel, will survive when screen rotation happens
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Executor
        val executor = ContextCompat.getMainExecutor(this)

        // Set up the BiometricPrompt
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Authentication successful
                // Navigate to the next screen or perform necessary action
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // Authentication error
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Authentication failed
            }
        })

        // Build the prompt info
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Log in using Face ID or password")
            .setNegativeButtonText("Use Password") // Password fallback button
            .build()

        // Check if biometric hardware is available and supported
        if (BiometricManager.from(this).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            // Launch the biometric prompt
            biometricPrompt.authenticate(promptInfo)
        }else  {
            // No biometrics enrolled - show password prompt
           // showPasswordPrompt()
        }
        setContent {
            Mvi_clean_room_hilt_flowsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                    // Add Fragment Container in Compose Layout
                    FragmentContainer()
                }
            }
        }
    }
}

@Composable
fun FragmentContainer() {
    AndroidView(factory = { context ->
        // Create a FragmentContainerView and set it to the FragmentManager
        val fragmentContainerView = FragmentContainerView(context).apply {
            id = View.generateViewId() // Generate an ID for the FragmentContainerView
        }

        // Add the fragment to the container
        val fragment = MovieListFragment()
        (context as? AppCompatActivity)?.supportFragmentManager?.commit {
            replace(fragmentContainerView.id, fragment)
        }

        fragmentContainerView
    })
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mvi_clean_room_hilt_flowsTheme {
        Greeting("Android")
    }
}
