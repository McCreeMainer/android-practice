package com.example.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.println(Log.DEBUG, "lifecycleLog", "ON_CREATE")
        super.onCreate(savedInstanceState)
    }
    override fun onDestroy() {
        Log.println(Log.DEBUG, "lifecycleLog", "ON_DESTROY")
        super.onDestroy()
    }
    override fun onStart() {
        Log.println(Log.DEBUG, "lifecycleLog", "ON_START")
        super.onStart()
    }
    override fun onStop() {
        Log.println(Log.DEBUG, "lifecycleLog", "ON_STOP")
        super.onStop()
    }
    override fun onRestart() {
        Log.println(Log.DEBUG, "lifecycleLog", "ON_RESTART")
        super.onRestart()
    }
    override fun onResume() {
        Log.println(Log.DEBUG, "lifecycleLog", "ON_RESUME")
        super.onResume()
    }
    override fun onPause() {
        Log.println(Log.DEBUG, "lifecycleLog", "ON_PAUSE")
        super.onPause()
    }
}
