package com.fungiggle.lexilink.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.annotation.RawRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SoundPlayer {
    private var soundPool: SoundPool? = null

    suspend fun init(context: Context) {
        withContext(Dispatchers.IO) {
            if (soundPool == null) {
                soundPool =
                    val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .build()

                        SoundPool.Builder()
                            .setMaxStreams(10)
                            .setAudioAttributes(audioAttributes)
                            .build()
            }
        }
    }

    suspend fun playSound(context: Context, @RawRes soundResId: Int) {
        withContext(Dispatchers.IO) {
            soundPool?.play(soundResId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}
