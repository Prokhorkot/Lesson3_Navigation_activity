package com.mirea.kotov.mireaproject

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder {
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer.create(this, R.raw.fornite_or_pubg)
        mediaPlayer!!.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
    }
}