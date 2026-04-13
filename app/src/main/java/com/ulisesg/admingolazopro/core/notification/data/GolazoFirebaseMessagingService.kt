package com.ulisesg.admingolazopro.core.notification.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ulisesg.admingolazopro.R
import com.ulisesg.admingolazopro.features.notification.domain.usecases.RegisterFCMTokenUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GolazoFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var registerFCMTokenUseCase: RegisterFCMTokenUseCase

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM_DEBUG", "Mensaje recibido de: ${message.from}")

        // Priorizar datos si vienen del backend personalizado
        val title = message.notification?.title ?: message.data["title"]
        val body = message.notification?.body ?: message.data["body"]

        Log.d("FCM_DEBUG", "Título: $title, Cuerpo: $body")

        if (title != null || body != null) {
            showNotification(title, body)
        }
    }

    private fun showNotification(title: String?, body: String?) {
        val channelId = "golazo_channel"
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Golazo Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notificaciones de pedidos y sistema"
            enableLights(true)
            enableVibration(true)
        }
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title ?: "AdminGolazo Pro")
            .setContentText(body ?: "Tienes una nueva notificación")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        Log.d("FCM_DEBUG", "Disparando notificación al sistema...")
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_DEBUG", "Nuevo token generado: $token")
        registerFCMTokenUseCase()
    }
}
