package com.ulisesg.admingolazopro.features.notification.data.repositories

import com.ulisesg.admingolazopro.features.notification.data.datasource.remote.api.TokenApi
import com.ulisesg.admingolazopro.features.notification.data.datasource.remote.api.FCMTokenDataSource
import com.ulisesg.admingolazopro.features.notification.data.datasource.remote.models.TokenRequest
import com.ulisesg.admingolazopro.features.notification.domain.repositories.FCMRepository
import javax.inject.Inject

class FCMRepositoryImpl @Inject constructor(
    private val api: TokenApi,
    private val dataSource: FCMTokenDataSource
) : FCMRepository {

    override fun getToken(onResult: (String?) -> Unit) {
        dataSource.getToken(onResult)
    }

    override suspend fun registerToken(token: String) {
        try {
            api.registerToken(TokenRequest(token))
        } catch (e: Exception) {
            // Manejar error de red
        }
    }
}
