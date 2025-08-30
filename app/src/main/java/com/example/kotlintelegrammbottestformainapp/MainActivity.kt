package com.example.kotlintelegrammbottestformainapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    private lateinit var buttonStart: Button
    private val BOT_TOKEN = "-"
    private val CHAT_ID = listOf("-", "-") // all main users's id  

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart = findViewById(R.id.button)

        buttonStart.setOnClickListener {
            sendTelegramMessage("Кнопка нажата")
        }
    }

    private fun sendTelegramMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = OkHttpClient()
                val encodedMessage = URLEncoder.encode(message, "UTF-8")

                for (CreatorsId in CHAT_ID) {
                    val url =
                        "https://api.telegram.org/bot$BOT_TOKEN/sendMessage?chat_id=$CreatorsId&text=$encodedMessage"

                    val request = Request.Builder()
                        .url(url)
                        .build()

                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            println("Error: ${response.code}")
                        }
                    }
                }
            } catch (e: Exception) {
                println("exept: ${e.message}")
            }
        }
    }
}
