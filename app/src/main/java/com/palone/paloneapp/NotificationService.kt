package com.palone.paloneapp

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val CHANNELID = "com.palone.paloneapp.channelID"
    val NOTIFICATIONID = 0
    override fun onMessageReceived(message: RemoteMessage) {

        message.notification?.let {
            if (!it.body.isNullOrEmpty() && !it.title.isNullOrEmpty()) sendNotification(
                it.body!!, it.title!!
            )
        }
        super.onMessageReceived(message)
    }

    private fun sendNotification(messageBody: String, title: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }
        val notificationManager = NotificationManagerCompat.from(this)
        val notification =
            NotificationCompat.Builder(this, CHANNELID).setContentTitle(title)
                .setContentText(title)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(messageBody)
                )
                .build()
        notificationManager.notify(NOTIFICATIONID, notification)

    }

}