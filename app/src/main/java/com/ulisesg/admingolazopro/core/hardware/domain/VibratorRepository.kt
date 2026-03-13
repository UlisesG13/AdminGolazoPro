package com.ulisesg.admingolazopro.core.hardware.domain

interface VibratorRepository {
    fun vibrate(duration: Long = 80)
}