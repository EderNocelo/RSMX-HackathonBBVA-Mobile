package com.mx.rsmobileog.tools

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build


class LocalNotificationManager : BroadcastReceiver() {

    companion object{
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
        val NOTIFICATION_CHANNEL_ID = "10009"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager: NotificationManager? = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification? = intent?.getParcelableExtra(NOTIFICATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            channel.vibrationPattern = longArrayOf(500,500)
            channel.enableVibration(true);
            channel.enableLights(false);
            channel.setSound(null, null);
            notificationManager!!.createNotificationChannel(channel)
        }

        val id: Int? = intent?.getIntExtra(NOTIFICATION_ID, 0)
        notificationManager!!.notify(id!!, notification)
    }
}