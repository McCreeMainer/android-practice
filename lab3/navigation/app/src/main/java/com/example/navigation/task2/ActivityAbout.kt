package com.example.navigation.task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigation.databinding.Task2ActivityAboutBinding

class ActivityAbout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.title = "Activity About"

        val binding = Task2ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}