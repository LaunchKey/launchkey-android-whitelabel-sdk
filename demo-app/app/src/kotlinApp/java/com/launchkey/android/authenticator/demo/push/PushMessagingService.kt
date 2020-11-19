package com.launchkey.android.authenticator.demo.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.ui.activity.ListDemoActivity
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager

class PushMessagingService : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.i(PushMessagingService::class.java.simpleName, "newToken=$newToken")
        // If this method is called, the push token has changed and the LK API
        // has to be notified.
        AuthenticatorManager.instance.setPushDeviceToken(newToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        notifyOfRequest(remoteMessage.notification)
        AuthenticatorManager.instance.handlePushPayload(remoteMessage.data)
    }

    private fun notifyOfRequest(optionalPushNotification: RemoteMessage.Notification?) {
        val context = applicationContext
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val tapIntent = Intent(context, ListDemoActivity::class.java)
        tapIntent.putExtra(ListDemoActivity.EXTRA_SHOW_REQUEST, true)
        val tapPendingIntent = PendingIntent.getActivity(context, 1, tapIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val channelId = createAndGetNotificationChannel(context, notificationManager)

        // Check if a push notification has defined a notification and use its title and body instead
        var title: String? = null
        var body: String? = null
        if (optionalPushNotification != null) {
            title = optionalPushNotification.title
            body = optionalPushNotification.body
        }
        if (title == null) {
            title = context.getString(R.string.app_name)
        }
        if (body == null) {
            body = context.getString(R.string.demo_notification_message)
        }
        val notification = NotificationCompat.Builder(context, channelId)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_stat_logo)
                .setColor(ContextCompat.getColor(context, R.color.demo_primary))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setContentIntent(tapPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createAndGetNotificationChannel(context: Context, notificationManager: NotificationManager): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = context.getString(R.string.demo_notification_channel_authrequests_name)
            val desc = context.getString(R.string.demo_notification_channel_authrequests_desc)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val id = context.getString(R.string.demo_notification_channel_authrequests_id)
            val channel = NotificationChannel(id, name, importance)
            channel.description = desc
            channel.importance = NotificationManager.IMPORTANCE_HIGH
            channel.setShowBadge(true)
            channel.enableLights(true)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
            return id
        }
        return "none"
    }

    companion object {
        const val NOTIFICATION_ID = 100
    }
}