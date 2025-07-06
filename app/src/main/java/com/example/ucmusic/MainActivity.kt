package com.example.ucmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucmusic.screen.DetailsScreen
import com.example.ucmusic.screen.MainScreen
// ELIMINADA: import com.example.ucmusic.ui.theme.UCMusicTheme
import com.example.ucmusic.viewmodel.RecognizerViewModel

class MainActivity : ComponentActivity() {

    private val recognizerViewModel: RecognizerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Ahora MyApp está directamente dentro de setContent
            MyApp(recognizerViewModel)
        }
    }
}

@Composable
fun MyApp(recognizerViewModel: RecognizerViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController, vm = recognizerViewModel)
        }
        composable("details") {
            DetailsScreen(navController = navController, vm = recognizerViewModel)
        }
    }
}