package com.example.ucmusic

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucmusic.screen.DetailsScreen
import com.example.ucmusic.screen.MainScreen
import com.example.ucmusic.screen.SplashScreen
import com.example.ucmusic.viewmodel.RecognizerViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val micPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(this, "Se necesita permiso de micrófono para reconocer música", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMicPermission()
        setContent {
            val vm: RecognizerViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return RecognizerViewModel(application) as T
                    }
                }
            )
            UCApp(vm)
        }
    }

    private fun requestMicPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }
}

@Composable
fun UCApp(vm: RecognizerViewModel) {
    val navController = rememberNavController()
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        NavigationGraph(navController, vm)
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, vm: RecognizerViewModel) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController, vm) }
        composable("details") { DetailsScreen(navController, vm) }
    }
}




