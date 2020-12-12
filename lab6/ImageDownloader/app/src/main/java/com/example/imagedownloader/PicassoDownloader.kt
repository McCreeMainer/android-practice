package com.example.imagedownloader

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.imagedownloader.Urls.url3
import com.example.imagedownloader.databinding.ActivityMainBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PicassoDownloader : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadButton.setOnClickListener {
            binding.loadButton.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            Picasso.get().load(url3).into(
                binding.img,
                object : Callback {

                    override fun onSuccess() {
                        binding.img.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                    }
                })
        }
    }
}