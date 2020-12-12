package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding

class CoroutinesWatch : AppCompatActivity() {

    private companion object {
        var secondsElapsed = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenStarted {
            while (isActive) {
                withContext(Dispatchers.Default) {
                    delay(1000)
                }

                withContext(Dispatchers.Main) {
                    binding.textSecondsElapsed.post {
                        binding.textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed++)
                    }
                }
            }
        }
    }
}