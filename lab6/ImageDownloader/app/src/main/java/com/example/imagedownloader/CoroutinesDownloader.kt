package com.example.imagedownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.imagedownloader.Urls.url2
import com.example.imagedownloader.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class CoroutinesDownloader : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener {
            binding.loadButton.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE

            lifecycleScope.launchWhenStarted {
                var bitmap: Bitmap? = null

                withContext(Dispatchers.IO) {
                    try {
                        @Suppress("BlockingMethodInNonBlockingContext")
                        val inputStream = URL(url2).openStream()
                        bitmap = BitmapFactory.decodeStream(inputStream)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                withContext(Dispatchers.Main) {
                    binding.img.setImageBitmap(bitmap)
                    binding.img.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}