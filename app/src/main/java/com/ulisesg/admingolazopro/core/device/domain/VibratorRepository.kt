package com.ulisesg.admingolazopro.core.device.domain

interface VibratorRepository {
    fun vibrate(duration: Long = 80)
}
