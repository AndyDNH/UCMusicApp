package com.example.ucmusic.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ucmusic.R
import com.example.ucmusic.viewmodel.RecognizerViewModel

@Composable
fun DetailsScreen(navController: NavHostController, vm: RecognizerViewModel) {
    // lastRecognizedSong debería contener la información correcta en este punto
    val songInfo = vm.lastRecognizedSong

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E2E))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detalles de la canción",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.logos2),
            contentDescription = "Carátula",
            modifier = Modifier.size(260.dp)
        )

        // Usamos 'let' para mostrar la información solo si songInfo no es nulo.
        // En teoría, con la nueva lógica, songInfo nunca debería ser nulo aquí.
        songInfo?.let {
            Text("Título: ${it.title}", color = Color.White, fontSize = 18.sp)
            Text("Artista: ${it.artist}", color = Color.White, fontSize = 18.sp)
            Text("Álbum: ${it.album ?: "Desconocido"}", color = Color.White, fontSize = 18.sp)
            Text("Año: ${it.year ?: "Desconocido"}", color = Color.White, fontSize = 18.sp)
            Text(
                "Género: ${it.genre ?: "Desconocido"}",
                color = Color.White,
                fontSize = 18.sp
            )
        } ?: run {
            // Esto es un fallback, pero no debería ocurrir con la nueva lógica de navegación.
            Text("Error: No se pudo cargar la información de la canción.", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // navegaUp() es la forma correcta de regresar a la pantalla anterior en la pila
            navController.navigateUp()
        }) {
            Text("Volver")
        }
    }
}