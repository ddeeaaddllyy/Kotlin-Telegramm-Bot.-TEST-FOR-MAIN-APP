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
    private val BOT_TOKEN = "8488953991:AAHhZ9WW6x7kwm8Ylh-Pd-XCPA9V9q8akvg"
    private val CHAT_ID = listOf("5893799306", "7031426620")

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
                            println("Error eblan: ${response.code}")
                        }
                    }
                }
            } catch (e: Exception) {
                println("exept: ${e.message}")
            }
        }
    }
}