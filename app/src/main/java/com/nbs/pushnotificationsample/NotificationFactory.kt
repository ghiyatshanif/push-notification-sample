package com.nbs.pushnotificationsample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

object NotificationFactory {

    private const val CHANNEL_ID = "1234"

    private const val CHANNEL_NAME = "TBIG_CHANNEL"

    fun buildNotification(
        remoteMessage: RemoteMessage,
        pendingIntent: PendingIntent?, context: Context
    ) {
        val notificationPayload = Gson().fromJson(Gson().toJson(remoteMessage.data), Payload::class.java)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = CHANNEL_ID
            val channelName = CHANNEL_NAME
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableLights(false)
            notificationChannel.setSound(defaultSoundUri, null)
            notificationChannel.enableVibration(false)

            notificationManager.createNotificationChannel(notificationChannel)

            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setContentText(notificationPayload.content)
                .setContentTitle(notificationPayload.title)
                .setOngoing(false)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)


            Glide.with(context)
                .asBitmap()
                .load(notificationPayload.imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        notificationBuilder.setLargeIcon(resource)
                        notificationBuilder.setStyle(NotificationCompat.BigPictureStyle()
                            .bigPicture(resource)
                            .bigLargeIcon(null)
                        )
                        notificationManager.notify(22, notificationBuilder.build())
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

        } else {
            val notificationBuilder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(notificationPayload.content)
                .setContentTitle(notificationPayload.title)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setSound(defaultSoundUri)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)

            Glide.with(context)
                .asBitmap()
                .load(notificationPayload.imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        notificationBuilder.setLargeIcon(resource)
                        notificationBuilder.setStyle(NotificationCompat.BigPictureStyle()
                            .bigPicture(resource)
                            .bigLargeIcon(null)
                        )
                        notificationManager.notify(22, notificationBuilder.build())
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }
}
