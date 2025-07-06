package com.example.ucmusic.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf // Importar para lista observable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.acrcloud.rec.*
import com.acrcloud.rec.IACRCloudListener
import com.example.ucmusic.model.SongInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

// (El resto de tus imports y el sealed class RecognitionEvent son los mismos)
sealed class RecognitionEvent {
    data class SongRecognized(val songInfo: SongInfo) : RecognitionEvent()
    object SongNotFound : RecognitionEvent()
    data class Error(val message: String) : RecognitionEvent()
    object Processing : RecognitionEvent()
}

class RecognizerViewModel(application: Application) :
    AndroidViewModel(application), IACRCloudListener {

    var lastRecognizedSong by mutableStateOf<SongInfo?>(null)
        private set

    // *** NUEVA ADICIÓN: Lista observable para el historial de canciones ***
    val recognizedSongsHistory = mutableStateListOf<SongInfo>()

    var isProcessing by mutableStateOf(false)
        private set

    private val _recognitionEvents = MutableSharedFlow<RecognitionEvent>()
    val recognitionEvents = _recognitionEvents.asSharedFlow()

    private val config = ACRCloudConfig().apply {
        context = application
        host = "identify-us-west-2.acrcloud.com"
        accessKey = "9127a01c3bdcd932636d1fc60c135a0a"
        accessSecret = "GJQACgAt24BD15OtPrjVCelokg6RP4FBoxjWmJDg"
        recorderConfig.apply {
            rate = 8000
            channels = 1
            isVolumeCallback = true
        }
        acrcloudListener = this@RecognizerViewModel
    }

    private val client: ACRCloudClient = ACRCloudClient().apply {
        initWithConfig(config).also {
            Log.d("ACR", "ACRCloudClient initWithConfig returned $it")
        }
    }

    var volume by mutableStateOf(0.0)
    var resultText by mutableStateOf("Idle")
    private var startTime = 0L

    fun start() {
        if (client.startRecognize()) {
            isProcessing = true
            startTime = System.currentTimeMillis()
            resultText = "Reconociendo…"
            lastRecognizedSong = null
            viewModelScope.launch {
                _recognitionEvents.emit(RecognitionEvent.Processing)
            }
        } else {
            resultText = "Error al iniciar el reconocimiento. Revisa permisos o configuración."
            isProcessing = false
            viewModelScope.launch {
                _recognitionEvents.emit(RecognitionEvent.Error(resultText))
            }
        }
    }

    fun cancel() {
        if (isProcessing) {
            client.cancel()
            isProcessing = false
            volume = 0.0
            resultText = "Cancelado"
        }
    }

    override fun onVolumeChanged(curVolume: Double) {
        volume = curVolume
    }

    override fun onResult(results: ACRCloudResult?) {
        isProcessing = false
        viewModelScope.launch {
            results?.result?.let {
                try {
                    val json = JSONObject(it)
                    if (json.getJSONObject("status").getInt("code") == 0) {
                        val songInfo = parseSongInfo(json)
                        if (songInfo != null) {
                            lastRecognizedSong = songInfo
                            // *** AÑADIR AL HISTORIAL ***
                            recognizedSongsHistory.add(0, songInfo) // Añadir al principio para que el más reciente esté arriba
                            resultText = "${songInfo.title} – ${songInfo.artist}"
                            _recognitionEvents.emit(RecognitionEvent.SongRecognized(songInfo))
                        } else {
                            resultText = "Error al procesar la información de la canción."
                            _recognitionEvents.emit(RecognitionEvent.Error(resultText))
                        }
                    } else {
                        resultText = "Canción no encontrada."
                        _recognitionEvents.emit(RecognitionEvent.SongNotFound)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    resultText = "Error de formato JSON en la respuesta: ${e.message}"
                    _recognitionEvents.emit(RecognitionEvent.Error(resultText))
                }
            } ?: run {
                resultText = "No se detectó resultado."
                _recognitionEvents.emit(RecognitionEvent.SongNotFound)
            }
        }
    }

    private fun parseSongInfo(json: JSONObject): SongInfo? {
        return try {
            val music = json.getJSONObject("metadata")
                .getJSONArray("music").getJSONObject(0)

            val title = music.getString("title")
            val artist = music.getJSONArray("artists")
                .getJSONObject(0).getString("name")
            val album = music.optJSONObject("album")?.optString("name") ?: "Desconocido"
            val year = music.optString("release_date", "N/A").take(4)
            val genre = music.optJSONArray("genres")?.optJSONObject(0)?.optString("name") ?: "N/A"

            SongInfo(title, artist, album, year, genre)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onCleared() {
        client.release()
        super.onCleared()
    }
}