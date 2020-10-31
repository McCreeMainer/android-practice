package com.example.navigation.task4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.navigation.R
import com.example.navigation.databinding.Task4Activity1Binding
import com.example.navigation.task2.ActivityAbout
import com.example.navigation.task3.Activity2

class Activity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task4Activity1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.app_bar))

        // To second
        binding.button1.setOnClickListener {
            startActivity(Intent(this, Activity2::class.java))
        }

        // To second (no history)
        binding.button2.setOnClickListener {
            startActivity(Intent(this, Activity2::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.about_button -> {
                    startActivity(Intent(this, ActivityAbout::class.java))
                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }
}