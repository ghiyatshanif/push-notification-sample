package com.nbs.pushnotificationsample.services

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nbs.pushnotificationsample.MainActivity
import com.nbs.pushnotificationsample.NotificationFactory

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()

        val refreshedToken = FirebaseInstanceId.getInstance().token
        if (!refreshedToken.isNullOrBlank()) {
            Log.d("DUDIDAM", "onCreate: $refreshedToken")
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        NotificationFactory.buildNotification(message, getPendingIntent(), this)
        Log.d("DUDIDAM", "onMessageReceived: $message")
    }

    override fun onNewToken(refreshedToken: String) {
        //get registration token
        Log.d("DUDIDAM", "onTokenRefresh: $refreshedToken")
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this,201,intent,PendingIntent.FLAG_ONE_SHOT)

    }
}