package com.example.imagedownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.imagedownloader.Urls.url1
import com.example.imagedownloader.databinding.ActivityMainBinding
import java.net.URL

class AsyncTaskDownloader : AppCompatActivity() {

    class ImageDownloader(private val binding: ActivityMainBinding) : AsyncTask<String, Unit, Bitmap>() {

        override fun onPreExecute() {
            binding.loadButton.visibility = INVISIBLE
            binding.progressBar.visibility = VISIBLE
        }

        override fun doInBackground(vararg params: String): Bitmap? {
            if (params.isEmpty()) {
                throw Exception("No URL")
            }

            val url = params[0]
            var bitmap: Bitmap? = null

            try {
                val inputStream = URL(url).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result == null) return

            binding.img.setImageBitmap(result)
            binding.img.visibility = VISIBLE
            binding.progressBar.visibility = INVISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener {
            ImageDownloader(binding).execute(url1)
        }
    }
}