package com.ulisesg.admingolazopro.features.notification.data.datasource.remote.api

import com.google.firebase.messaging.FirebaseMessaging

class FCMTokenDataSource {

    fun getToken(onResult: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    onResult(null)
                    return@addOnCompleteListener
                }
                onResult(task.result)
            }
    }
}
