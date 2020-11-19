package com.launchkey.android.authenticator.demo.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.ui.activity.ListDemoActivity;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;

public class PushMessagingService extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 100;

    @Override
    public void onNewToken(String newToken) {
        super.onNewToken(newToken);

        Log.i(PushMessagingService.class.getSimpleName(), "newToken=" + newToken);
        // If this method is called, the push token has changed and the LK API
        // has to be notified.
        AuthenticatorManager.instance.setPushDeviceToken(newToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        notifyOfRequest(remoteMessage.getNotification());
        AuthenticatorManager.instance.handlePushPayload(remoteMessage.getData());
    }

    private void notifyOfRequest(RemoteMessage.Notification optionalPushNotification) {
        Context context = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) context.
                getSystemService(Context.NOTIFICATION_SERVICE);
        Intent tapIntent = new Intent(context, ListDemoActivity.class);
        tapIntent.putExtra(ListDemoActivity.EXTRA_SHOW_REQUEST, true);

        PendingIntent tapPendingIntent = PendingIntent.getActivity(context, 1, tapIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = createAndGetNotificationChannel(context, notificationManager);

        // Check if a push notification has defined a notification and use its title and body instead
        String title = null, body = null;

        if (optionalPushNotification != null) {
            title = optionalPushNotification.getTitle();
            body = optionalPushNotification.getBody();
        }

        if (title == null) {
            title = context.getString(R.string.app_name);
        }

        if (body == null) {
            body = context.getString(R.string.demo_notification_message);
        }

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_stat_logo)
                .setColor(ContextCompat.getColor(context, R.color.demo_primary))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentIntent(tapPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private String createAndGetNotificationChannel(final Context context, final NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.demo_notification_channel_authrequests_name);
            String desc = context.getString(R.string.demo_notification_channel_authrequests_desc);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String id = context.getString(R.string.demo_notification_channel_authrequests_id);

            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(desc);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            channel.enableLights(true);
            channel.enableVibration(true);

            notificationManager.createNotificationChannel(channel);

            return id;
        }

        return "none";
    }
}
