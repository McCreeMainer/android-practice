package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class AsyncTaskWatch : AppCompatActivity() {

    private lateinit var counterTask: CounterTask
    private var isUIUpdaterStopped = false

    class CounterTask(private val changeUI: () -> Unit) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            while (!isCancelled) {
                TimeUnit.SECONDS.sleep(1)
                    publishProgress()
            }
        }

        override fun onProgressUpdate(vararg values: Unit?) {
            super.onProgressUpdate(*values)
            changeUI()
        }
    }

    private companion object {
        var secondsElapsed = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let { secondsElapsed = it.getInt("SECONDS_ELAPSED") }
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        counterTask = CounterTask {
            binding.textSecondsElapsed.post {
                binding.textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed++)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        counterTask.execute()
    }

    override fun onStop() {
        super.onStop()
        counterTask.cancel(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        isUIUpdaterStopped = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SECONDS_ELAPSED", secondsElapsed)
    }
}