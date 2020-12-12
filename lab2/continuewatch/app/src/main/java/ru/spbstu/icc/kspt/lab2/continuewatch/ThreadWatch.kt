package ru.spbstu.icc.kspt.lab2.continuewatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding

//class MainActivity : AppCompatActivity() {
//    var secondsElapsed: Int = 0
//
//    var backgroundThread = Thread {
//        while (true) {
//            Thread.sleep(1000)
//            textSecondsElapsed.post {
//                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
//                Log.println(Log.DEBUG, "checkWhenOnStop", "$secondsElapsed")
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        backgroundThread.start()
//    }
//}


class ThreadWatch : AppCompatActivity() {

    private lateinit var currentCounter: StoppableCounter
    private lateinit var uiUpdater: Thread
    private var isUIUpdaterStopped = false

    private class StoppableCounter {
        private var isStopped = false
        private val thread = Thread {
            while (!isStopped) {
                Thread.sleep(1000)
                if (isStopped) break
                secondsElapsed++
            }
        }
        fun start() {
            thread.start()
        }
        fun stop() {
            isStopped = true
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

        uiUpdater = Thread {
            while (!isUIUpdaterStopped) {
                Thread.sleep(1000)
                if (isUIUpdaterStopped) break
                binding.textSecondsElapsed.post {
                    binding.textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed)
                }
            }
        }
        uiUpdater.start()
    }

    override fun onStart() {
        super.onStart()
        currentCounter = StoppableCounter()
        currentCounter.start()
    }


    override fun onStop() {
        super.onStop()
        currentCounter.stop()
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