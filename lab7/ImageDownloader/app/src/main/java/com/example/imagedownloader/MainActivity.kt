package com.example.imagedownloader

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.imagedownloader.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL

class DownloaderService : Service() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra("URL")

        if (url != null) {
            scope.launch {
                Log.i("Downloader", "Current Thread: ${Thread.currentThread().name}")

                val imgLoc = downloadImage(url)?.saveImage(filesDir) ?: return@launch

                sendBroadcast(Intent("com.example.imagedownloader.IMG_DOWNLOADED").apply {
                    putExtra("IMG_LOC", imgLoc)
                })
                Log.i("Downloader", "Image location: $imgLoc")

                stopSelf(startId)
            }
        } else stopSelf(startId)

        return START_NOT_STICKY
    }

    private fun downloadImage(url: String): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            val inputStream = URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun Bitmap.saveImage(dir: File): String {
        var index = 0

        for (file in dir.list() ?: emptyArray())
            if (Regex("""img_(\d+)\.png""").matches(file)) index++

        val filename = "img_${index}.png"

        openFileOutput(filename, MODE_PRIVATE).use {
            compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        return File(filesDir, filename).absolutePath
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        scope.cancel()
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener {
            startService(Intent(this, DownloaderService::class.java).apply {
                putExtra("URL", Urls.url2)
            })
        }
    }
}