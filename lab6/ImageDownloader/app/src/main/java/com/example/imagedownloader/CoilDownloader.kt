package com.example.imagedownloader

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.imagedownloader.Urls.url3
import com.example.imagedownloader.databinding.ActivityMainBinding

class CoilDownloader : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener {
            binding.loadButton.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.img.load(url3) {
                listener(
                    onError = { _, e: Throwable ->
                        e.printStackTrace()
                    },
                    onSuccess = { _, _ ->
                        binding.img.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                )
            }
        }


    }
}