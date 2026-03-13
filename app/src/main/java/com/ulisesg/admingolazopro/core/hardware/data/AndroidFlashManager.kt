package com.ulisesg.admingolazopro.core.hardware.data

import android.content.Context
import android.hardware.camera2.CameraManager
import com.ulisesg.admingolazopro.core.hardware.domain.FlashManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.delay

class AndroidFlashManager @Inject constructor(
    @ApplicationContext private val context: Context
) : FlashManager {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val cameraId: String? by lazy {
        try { cameraManager.cameraIdList.firstOrNull() } catch (e: Exception) { null }
    }

    override fun turnOn() {
        setFlashState(true)
    }

    override fun turnOff() {
        setFlashState(false)
    }

    override fun hasFlash(): Boolean =
        context.packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_CAMERA_FLASH)

    override suspend fun blink(durationMillis: Long) {
        if (!hasFlash()) return
        turnOn()
        delay(durationMillis)
        turnOff()
    }

    private fun setFlashState(isEnabled: Boolean) {
        cameraId?.let {
            try {
                cameraManager.setTorchMode(it, isEnabled)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
