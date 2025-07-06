package com.example.ucmusic.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.acrcloud.rec.*
import com.acrcloud.rec.IACRCloudListener
import com.example.ucmusic.model.SongInfo
import org.json.JSONException
import org.json.JSONObject

class RecognizerViewModel(application: Application) :
    AndroidViewModel(application), IACRCloudListener {

    var lastRecognizedSong by mutableStateOf<SongInfo?>(null)
        private set

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
            Log.d("ACR", "initWithConfig returned $it")
        }
    }

    var volume by mutableStateOf(0.0)
    var resultText by mutableStateOf("Idle")
    var processing by mutableStateOf(false)
    private var startTime = 0L

    fun start() {
        if (client.startRecognize()) {
            processing = true
            startTime = System.currentTimeMillis()
            resultText = "Reconociendo…"
        } else {
            resultText = "Error init"
        }
    }

    fun cancel() {
        if (processing) {
            client.cancel()
            reset()
        }
    }

    private fun reset() {
        processing = false
        volume = 0.0
    }

    override fun onVolumeChanged(curVolume: Double) {
        volume = curVolume
    }

    override fun onResult(results: ACRCloudResult?) {
        processing = false
        results?.result?.let {
            resultText = parseTitleArtist(it)
        } ?: run {
            resultText = "No se detectó resultado"
        }
    }

    private fun parseTitleArtist(jsonStr: String): String {
        return try {
            val json = JSONObject(jsonStr)
            if (json.getJSONObject("status").getInt("code") == 0) {
                val music = json.getJSONObject("metadata")
                    .getJSONArray("music").getJSONObject(0)

                val title = music.getString("title")
                val artist = music.getJSONArray("artists")
                    .getJSONObject(0).getString("name")
                val album = music.optJSONObject("album")?.optString("name") ?: "Desconocido"
                val year = music.optString("release_date", "N/A").take(4)
                val genre = music.optJSONArray("genres")?.optJSONObject(0)?.optString("name") ?: "N/A"

                lastRecognizedSong = SongInfo(title, artist, album, year, genre)

                "$title – $artist"
            } else {
                jsonStr
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            jsonStr
        }
    }

    override fun onCleared() {
        client.release()
        super.onCleared()
    }
}