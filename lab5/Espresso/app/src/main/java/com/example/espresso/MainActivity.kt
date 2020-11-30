package com.example.espresso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.espresso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(layoutBinding.root)

        layoutBinding.button.setOnClickListener {
            layoutBinding.button.text = getString(R.string.on_click_button_text)
        }
    }
}