package com.example.ucmusic.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn // Importar LazyColumn
import androidx.compose.foundation.lazy.items // Importar items para LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider // Opcional: para separar elementos en la lista
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ucmusic.R
import com.example.ucmusic.viewmodel.RecognitionEvent
import com.example.ucmusic.viewmodel.RecognizerViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(navController: NavHostController, vm: RecognizerViewModel) {
    val isEcuadorianSong = false
    val snackbarHostState = remember { SnackbarHostState() }

    val backgroundColor = if (isEcuadorianSong) Color(0xFFFFE500) else Color(0xFFBBDEFB)
    val textColor = if (isEcuadorianSong) Color(0xFF002147) else Color.Black

    LaunchedEffect(Unit) {
        vm.recognitionEvents.collectLatest { event ->
            when (event) {
                is RecognitionEvent.SongRecognized -> {
                    navController.navigate("details") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                is RecognitionEvent.SongNotFound -> {
                    snackbarHostState.showSnackbar(
                        message = "Canción no encontrada. Intenta de nuevo.",
                        withDismissAction = true
                    )
                }
                is RecognitionEvent.Error -> {
                    snackbarHostState.showSnackbar(
                        message = "Error: ${event.message}. Por favor, intenta de nuevo.",
                        withDismissAction = true
                    )
                }
                is RecognitionEvent.Processing -> {
                    // Estado de procesamiento, ya manejado por el texto del botón
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SnackbarHost(hostState = snackbarHostState)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (isEcuadorianSong) "¡Música Ecuatoriana Detectada!" else "",
            fontSize = 24.sp,
            color = textColor,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.logos2),
            contentDescription = null,
            modifier = Modifier.size(380.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Pulsa el botón para reconocer una canción",
            color = textColor,
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                vm.start()
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
            modifier = Modifier.fillMaxWidth(0.8f),
            enabled = !vm.isProcessing
        ) {
            Text(
                text = if (vm.isProcessing) "Reconociendo..." else "Reconocer Canción",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Reconociste estas canciones",
            color = textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        // *** CAMBIO AQUÍ: Mostrar el historial de canciones ***
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            if (vm.recognizedSongsHistory.isEmpty()) {
                Text(
                    text = "Aquí aparecerán tus canciones reconocidas",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // Usamos LazyColumn para desplazar si hay muchas canciones
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(vm.recognizedSongsHistory) { song ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Título: ${song.title}", color = Color.Black, fontSize = 14.sp)
                            Text(text = "Artista: ${song.artist}", color = Color.Gray, fontSize = 12.sp)
                            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}