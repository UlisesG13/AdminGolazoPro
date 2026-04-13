package com.ulisesg.admingolazopro.features.notification.domain.repositories

interface FCMRepository {
    suspend fun registerToken(token: String)
    fun getToken(onResult: (String?) -> Unit)
}
