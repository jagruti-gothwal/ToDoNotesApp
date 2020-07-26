package com.jagruti.todonotesapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jagruti.todonotesapp.R

class MyFirebaseMessagingService : FirebaseMessagingService(){
    val TAG = "FirebaseMsg"
    override fun onMessageReceived(message: RemoteMessage) {
            super.onMessageReceived(message)
            Log.d(TAG,message.from)
            Log.d(TAG,message.notification?.body)
            setupNotification(message.notification?.body)
    }

    private fun setupNotification(body: String?) {
        val channelId="Todo Id"
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.task)
                .setContentTitle("ToDo Notes App")
                .setContentText(body)
                .setSound(ringtone)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,"Todo-Notes",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0,notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}