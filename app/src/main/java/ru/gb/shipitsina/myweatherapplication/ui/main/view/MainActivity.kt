package ru.gb.shipitsina.myweatherapplication.ui.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import ru.gb.shipitsina.myweatherapplication.R
import ru.gb.shipitsina.myweatherapplication.databinding.MainActivityBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collector
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    private val binding: MainActivityBinding by lazy{
        MainActivityBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

      /* binding.ok.setOnClickListener{
            val uri = URL(binding.url.text.toString())

            val handler = Handler(Looper.getMainLooper())

            Thread{
                goToInternet(uri, handler)
            }.start()

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun goToInternet(uri: URL, handler: Handler) {
        var urlConnection: HttpsURLConnection? = null

        try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.apply {
                requestMethod = "GET"
                readTimeout = 10000
            }
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = reader.lines().collect(Collectors.joining("\n"))

            handler.post{
                binding.webview.loadData(result,"text/html; charset=utf-8","utf-8")
            }



        } catch (e: Exception){
            Log.e("","FAILED",e)
        } finally {
            urlConnection?.disconnect()
        }*/
    }
}