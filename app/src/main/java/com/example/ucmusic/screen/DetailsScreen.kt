package com.example.ucmusic.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ucmusic.R
import com.example.ucmusic.viewmodel.RecognizerViewModel

@Composable
fun DetailsScreen(navController: NavHostController, vm: RecognizerViewModel) {
    val songInfo = vm.lastRecognizedSong

    val ecuatorianArtists = listOf(
        // Clásicos y tradición
        "Carmencita Lara",
        "Julio Jaramillo",
        "Carlota Jaramillo",
        "Toty Rodríguez",
        "Olga Gutiérrez",
        "Petita Palma",
        "Carlos Rubira Infante",
        "Dúo Benítez Valencia",
        "Seg Condori",
        "Don Medardo y sus Players",
        "Hermanos Miño Naranjo",
        "Paulina Tamayo",
        "Hilda Murillo",
        "Margarita Laso",
        "Maria Tejada",

        // Pop / Andipop / Balada
        "Juan Fernando Velasco",
        "Mirella Cesa",
        "Daniel Betancourth",
        "Fausto Miño",
        "Paola Navarrete",
        "Gabriela Villalba",
        "Paula Aguirre",
        "Nikki Mackliff",
        "Pamela Cortés",
        "Ren Kai",

        // Rock / Indie / Alternativo
        "Verde 70",
        "Tranzas",
        "Lolabúm",
        "La Máquina Camaleón",
        "Sal y Mileto",
        "Da Pawn",
        "Mamá soy demente",
        "Guardarraya",
        "Krucs en Karnak",

        // Urbano / Hip‑hop / Rap
        "AU‑D",
        "Guanaco MC",
        "Mala Fama",
        "EVHA",
        "Gerardo",
        "Danilo Parra",

        // Tecno / Electrónica / Experimental
        "Swing Original Monks",
        "Nicola Cruz",
        "Quixosis",
        "Mateo Kingman",
        "Humazapas",
        "Mala Fama",
        "Fabrikante",

        // Tecnocumbia / Folklore mestizo
        "Sharon la Hechicera",
        "Delfín Quishpe",
        "Nelly Janeth",

        // Nuevos sonidos tradicionales / fusión
        "Tamya Morán",
        "Mariela Condo",
        "Ricardo Pita",
        "Sudakaya",

        // Otros géneros & talentos diversos
        "Aladino",
        "Papa Chango",
        "Mikrofon",
        "Marqués",
        "Daniel Beta",
        "María Tejada",
        "Alex Ponce",

        // Cumbia / tecnocumbia / cumbia andina / tropical
        "Don Medardo y sus Players",
        "Papa Chango",
        "La Vagancia",
        "Los Corrientes",
        "Papaya Dada",
        "Los Chaucha Kings",
        "Playeros Kichwas",
        "Gustavo Velasquez",
        "Angel Velasquez",
        "Widinson",

        // Pasillo (clasico y contemporaneo)
        "Julio Jaramillo",
        "Pepe Jaramillo",
        "Carlota Jaramillo",
        "Olga Gutierrez",
        "Hermanas Mendoza Suasti",
        "Hermanas Sangurima",
        "Maxima Mejia",
        "Hilda Murillo",
        "Duo Benitez Valencia",
        "Hermanos Miño Naranjo",
        "Hermanos Nuñez",
        "Los Brillantes",
        "Duo Ecuador",
        "Jesus Vasquez",
        "Tito del Salto",
        "Kike Vega",
        "Irma Arauz",
        "Liliam Suarez",
        "Eduardo Brito",
        "Pepe Jaramillo",
        "Nicolas Fiallos",
        "Maria Tejada",
        "Margarita Laso",
        "Carlos Grijalva",
        "Alexandra Cabanilla",
        "Gerardo Moran",
        "La Toquilla",
        "Daniel Paez",
        "Fresia Saavedra",
        "Mélida Maria Jaramillo",
        "Consuelo Vargas",
        "Daniel Realpe",
        "Misquilla",

        // Rockola (añadidos previamente)
        "Aladino",
        "Segundo Rosero",
        "Chugo Tobar",
        "Roberto Calero",
        "Jenny Rosero",
        "Cecilio Alva",
        "Juanita Burbano",
        "Elias Vera",
        // Clasicos populares, tecnocumbia y nacional
        "Maximo Escaleras",
        "Juanita Burbano",
        "Gustavo Velasquez",
        "Angel Velasquez",
        "Widinson",
        "Fanny Jauch",
        "Jorge Luis del Hierro",
        "Fresia Saavedra",
        "Edgar Palacios",
        "Paulina Tamayo",
        "Gerardo Moran",
        "Delfin Quishpe",
        "Sharon la Hechicera",
        "Martha Velasco",
        "Franklin Urrutia",
        "Los Hermanos Aymara",
        "Luis Aymara",
        "Polo Aymara",
        "Chinito del Ande",
        "Cecilia Canta",
        "Hermanos Nunez",
        "Los Reales",
        "Rocio Jurado Ecuador",
        "Las Chicas Dulces",
        "Los Brillantes",
        "Las Hermanas Mendoza Suasti",
        "Las Hermanas Sangurima",

        // Rockola
        "Aladino",
        "Segundo Rosero",
        "Chugo Tobar",
        "Roberto Calero",
        "Jenny Rosero",
        "Cecilio Alva",
        "Elias Vera",

        // Pasillo clasico y moderno
        "Julio Jaramillo",
        "Carlota Jaramillo",
        "Olga Gutierrez",
        "Duo Benitez Valencia",
        "Hilda Murillo",
        "Margarita Laso",
        "Carlos Rubira Infante",
        "Maria Tejada",
        "Nicolas Fiallos",
        "Maxima Mejia",
        "Duo Ecuador",
        "Tito del Salto",
        "Irma Arauz",
        "Eduardo Brito",
        "Misquilla",
        "La Toquilla",
        "Daniel Paez",
        "Alexandra Cabanilla",

        // Cumbia / tecnocumbia / tropical
        "Don Medardo y sus Players",
        "Los Corrientes",
        "Papaya Dada",
        "Chaucha Kings",
        "La Vagancia",
        "Papa Chango",

        // Rock / indie / alternativo
        "Verde 70",
        "Tranzas",
        "La Maquina Camaleon",
        "Lolabum",
        "Da Pawn",
        "Guardarraya",
        "Rocola Bacalao",
        "Kruks en Karnak",
        "Sal y Mileto",

        // Pop / balada
        "Juan Fernando Velasco",
        "Fausto Mino",
        "Daniel Betancourth",
        "Mirella Cesa",
        "Pamela Cortes",
        "Gabriela Villalba",
        "Nikki Mackliff",
        "Johann Vera",

        // Urbano / hip hop / nuevo pop
        "AU-D",
        "Guanaco MC",
        "Tres Dedos",
        "Ren Kai",
        "Mexicoke",
        "Fiebre",
        "Andreina Bravo",
        "Dayanara",
        "Jombriel",
        "Alex Ponce",
        "Camila Perez",
        "Ceci Juno",
        "Mia Teran",
        "Shalom Mendieta",
        "Jorsh JMP",

        // Electronica / fusion / andino moderno
        "Nicola Cruz",
        "Mateo Kingman",
        "Quixosis",
        "Humazapas",
        "Fabrikante",
        "Daniela Alban",
        "Neoma",
        "Tonicamo",
        "La Madre Tirana",
        "KAIFO",
        "Luz Pinos",
        "Alex Eugenio",
        "Tamya Moran",
        "Mariela Condo",
        "Ricardo Pita",
        "Inmortal Kultura"
        )

    val isEcuadorian = songInfo?.artist?.let { artist ->
        ecuatorianArtists.any { knownArtist ->
            artist.contains(knownArtist, ignoreCase = true)
        }
    } ?: false

    val backgroundColor = if (isEcuadorian) Color(0xFFFCD116) else Color(0xFF1E1E2E)
    val textColor = if (isEcuadorian) Color(0xFF003893) else Color.White
    val logo = if (isEcuadorian) R.drawable.logoec1 else R.drawable.logos2

    // Animaciones para fade-in y scale
    val animationDuration = 800
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = animationDuration)
    )
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = animationDuration)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detalles de la canción",
            fontSize = 28.sp,
            color = textColor,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.3f),
                    blurRadius = 8f
                )
            ),
            modifier = Modifier.scale(scale).alpha(alpha)
        )

        // Logo con borde y sombra para destacar
        Image(
            painter = painterResource(id = logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(30.dp))
                .border(
                    width = 5.dp,
                    color = if (isEcuadorian) Color(0xFF003893) else Color.Gray,
                    shape = RoundedCornerShape(30.dp)
                )
                .shadow(10.dp, RoundedCornerShape(30.dp))
                .scale(scale)
                .alpha(alpha)
        )

        if (isEcuadorian) {
            Text(
                text = "¡Orgullo nacional 🇪🇨!",
                color = Color(0xFF003893),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.alpha(alpha),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.25f),
                        blurRadius = 6f
                    )
                )
            )
        }

        songInfo?.let {
            Column(
                modifier = Modifier.alpha(alpha),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Título: ${it.title}", color = textColor, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text("Artista: ${it.artist}", color = textColor, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text("Álbum: ${it.album ?: "Desconocido"}", color = textColor, fontSize = 18.sp)
                Text("Año: ${it.year ?: "Desconocido"}", color = textColor, fontSize = 18.sp)
                Text("Género: ${it.genre ?: "Desconocido"}", color = textColor, fontSize = 18.sp)
            }
        } ?: run {
            Text("Error: No se pudo cargar la información de la canción.", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigateUp() }) {
            Text("Volver")
        }
    }
}
