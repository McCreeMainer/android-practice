package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.broadcastreceiver.databinding.ActivityMainBinding

class DownloadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("DownloadReceiver", "Received intent")

        val location = intent?.getStringExtra("IMG_LOC")
                ?: return

        val resultIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("IMG_LOC", location)
        }

        context?.startActivity(resultIntent)
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filter = IntentFilter()
        filter.addAction("com.example.imagedownloader.IMG_DOWNLOADED")
        registerReceiver(DownloadReceiver(), filter)

        val location = intent?.getStringExtra("IMG_LOC")

        if (location != null) {
            binding.text.text = getString(R.string.location, location)
        }
    }
}