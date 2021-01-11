package com.example.boundimagedownloader

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.boundimagedownloader.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import java.lang.ref.WeakReference
import java.net.URL

class Messages {
    companion object {
        const val LOAD_IMAGE = 0
        const val IMAGE_LOCATION = 1
    }
}

class DownloadHandler(service: DownloadService) : Handler(service.mainLooper) {
    private val serviceReference = WeakReference(service)

    override fun handleMessage(message: Message) {
        if (message.what == Messages.LOAD_IMAGE) {
            Log.i("DownloadHandler", "Download message incoming")

            val url = message.obj as? String ?: return
            val replyTo = message.replyTo ?: return

            serviceReference.get()?.saveImage(url) {
                Log.i("DownloadHandler", "Sending response: $it")
                replyTo.send(Message.obtain().apply {
                    obj = it
                    what = Messages.IMAGE_LOCATION
                })
            }
        } else super.handleMessage(message)
    }
}

class ResponseHandler(activity: MainActivity) : Handler(activity.mainLooper) {
    private val activityRef = WeakReference(activity)

    override fun handleMessage(message: Message) {
        if (message.what == Messages.IMAGE_LOCATION) {
            Log.d("ResponseHandler", "Save message incoming")

            val location = message.obj as? String ?: return

            activityRef.get()?.showImg(location) ?: return
        } else super.handleMessage(message)
    }
}

class DownloadService : Service() {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun saveImage(url: String, saver: suspend (String) -> Unit) {
        scope.launch {
            downloadImage(url)?.saveImage(filesDir)?.let {
                saver(it)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra("URL")

        if (url != null) {
            saveImage(url) {
                Log.i("DownloadService", "Image location: $it")

                sendBroadcast(Intent("com.example.imagedownloader.IMG_DOWNLOADED").apply {
                    putExtra("IMG_LOC", it)
                })

                stopSelf(startId)
            }
        } else stopSelf(startId)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return Messenger(DownloadHandler(this)).binder
    }

    override fun onDestroy() {
        scope.cancel()
    }

    private fun downloadImage(url: String): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            val inputStream = URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun Bitmap.saveImage(dir: File): String {
        var index = 0

        for (file in dir.list() ?: emptyArray())
            if (Regex("""img_(\d+)\.png""").matches(file)) index++

        val filename = "img_${index}.png"

        openFileOutput(filename, MODE_PRIVATE).use {
            compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        return File(filesDir, filename).absolutePath
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var messenger: Messenger

    private val connection = object : ServiceConnection {
        var messenger: Messenger? = null

        fun isActive() = messenger != null

        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            Log.i("MainActivity", "Service connected")
            messenger = Messenger(service)
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            Log.i("MainActivity", "Service disconnected")
            messenger = null
        }
    }

    fun showImg(location: String) {
        binding.text.text = this.getString(R.string.location, location)
        binding.img.setImageBitmap(BitmapFactory.decodeFile(location))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startService(Intent(this, DownloadService::class.java).apply {
                putExtra("URL", Urls.url3)
            })
        }

        val intent = Intent(this, DownloadService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        messenger = Messenger(ResponseHandler(this))

        binding.requestButton.setOnClickListener {
            if (!connection.isActive()) {
                Log.i("MainActivity", "Service is currently disconnected")
                return@setOnClickListener
            }

            val message = Message.obtain().apply {
                obj = Urls.url1
                replyTo = messenger
                what = Messages.LOAD_IMAGE
            }

            try {
                connection.messenger?.send(message)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}