package com.ulisesg.admingolazopro.core.device.domain

interface FlashManager {
    fun turnOn()
    fun turnOff()
    suspend fun blink(durationMillis: Long = 100)
    fun hasFlash(): Boolean
}

