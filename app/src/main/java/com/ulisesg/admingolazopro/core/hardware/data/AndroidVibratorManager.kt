package com.ulisesg.admingolazopro.core.hardware.data

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.ulisesg.admingolazopro.core.hardware.domain.VibratorRepository
import android.os.VibratorManager as SystemVibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidVibratorManager @Inject constructor(
    @ApplicationContext private val context: Context
) : VibratorRepository {

    override fun vibrate(duration: Long) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            val manager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as SystemVibratorManager

            manager.defaultVibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )

        } else {

            val vibrator =
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            vibrator.vibrate(duration)
        }
    }
}