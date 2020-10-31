package com.example.navigation.task2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.navigation.R
import com.example.navigation.databinding.Task2Activity2Binding

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task2Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.app_bar))
        actionBar?.title = "Activity 2"

        // To first
        binding.button1.setOnClickListener {
            finish()
        }

        // To third
        binding.button2.setOnClickListener {
            startActivityForResult(Intent(this, Activity3::class.java), START_ACTIVITY)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == START_ACTIVITY
                && resultCode == RESULT_OK) {
                val activitiesToDestroy = data
                        ?.getIntExtra(ACTIVITIES_TO_DESTROY, 0)
                        ?: 0

                if (activitiesToDestroy > 0) {
                    setResult(RESULT_OK, Intent().apply {
                        putExtra(ACTIVITIES_TO_DESTROY, activitiesToDestroy - 1)
                    })
                    finish()
                }
        }
    }
}