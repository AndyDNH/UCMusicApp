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

        if (songInfo != null) {
            Text("Título: ${songInfo.title}", color = Color.White, fontSize = 18.sp)
            Text("Artista: ${songInfo.artist}", color = Color.White, fontSize = 18.sp)
            Text("Álbum: ${songInfo.album ?: "Desconocido"}", color = Color.White, fontSize = 18.sp)
            Text("Año: ${songInfo.year ?: "Desconocido"}", color = Color.White, fontSize = 18.sp)
            Text(
                "Género: ${songInfo.genre ?: "Desconocido"}",
                color = Color.White,
                fontSize = 18.sp
            )
        } else {
            Text("No hay información de canción disponible", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.navigateUp()
        }) {
            Text("Volver")
        }
    }
}