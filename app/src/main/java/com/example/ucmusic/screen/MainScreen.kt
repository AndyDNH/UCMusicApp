package com.example.ucmusic.screen

import android.app.Application
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ucmusic.R
import com.example.ucmusic.viewmodel.RecognizerViewModel
import androidx.lifecycle.Lifecycle


@Composable
fun MainScreen(navController: NavHostController, vm: RecognizerViewModel) {
    val isEcuadorianSong = false

    val backgroundColor = if (isEcuadorianSong) Color(0xFFFFE500) else Color(0xFFBBDEFB)
    val textColor = if (isEcuadorianSong) Color(0xFF002147) else Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            enabled = !vm.processing
        ) {
            Text(
                text = if (vm.processing) "Reconociendo..." else "Reconocer Canción",
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            Text(
                text = "Aquí aparecerán tus canciones reconocidas",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LaunchedEffect(vm.lastRecognizedSong) {
            if (!vm.processing && vm.lastRecognizedSong != null) {
                navController.navigate("details") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
