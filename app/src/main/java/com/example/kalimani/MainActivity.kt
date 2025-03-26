package com.example.kalimani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.kalimani.presentation.Screens.home.HomeScreen
import com.example.kalimani.presentation.Screens.onBoarding.OnboardingScreen
import com.example.kalimani.ui.theme.KaliManiTheme
import com.example.kalimani.presentation.navigation.App
import com.example.kalimani.presentation.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
               Color(0xFF843014).toArgb()
            )
        )
        setContent {
            KaliManiTheme {
                Surface {

                    MainNavigation()
                  //  HomeScreen()
                }
            }
        }
    }
}

