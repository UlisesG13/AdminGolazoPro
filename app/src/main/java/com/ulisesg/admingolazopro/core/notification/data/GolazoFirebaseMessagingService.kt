package com.ulisesg.admingolazopro.core.notification.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ulisesg.admingolazopro.MainActivity
import com.ulisesg.admingolazopro.R

class GolazoFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        remoteMessage.notification?.let {
            showNotification(
                it.title ?: "Nuevo Pedido",
                it.body ?: "Tienes un nuevo pedido en espera"
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("FCM Token: $token")
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "orders_channel"
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Pedidos Nuevos",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }
}
