package com.example.homeworkout.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.homeworkout.R
import com.example.homeworkout.ui.main.MainScreen
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification?.title ?:""
            val body = remoteMessage.notification?.body ?:""
            sendNotification(title, body)
        }
    }
    private fun sendNotification(title: String, content: String) {
        // intent, prepare data
        val intent = Intent(this, MainScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.action = System.currentTimeMillis().toString()
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("content", content)

        val resultPendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)

        notificationBuilder.setVibrate(longArrayOf(100, 100))
        notificationBuilder.setLights(Color.GREEN, 10000, 10000)

        var notificationManager: NotificationManager? = null
        notificationManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(NotificationManager::class.java)
        } else {
            this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1",
                resources.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.setShowBadge(true)
            channel.enableVibration(true)
            channel.enableLights(true)
            channel.vibrationPattern = longArrayOf(400, 200, 400)
            notificationManager?.createNotificationChannel(channel)
        }
        notificationManager?.notify(1, notificationBuilder.build())
    }
}