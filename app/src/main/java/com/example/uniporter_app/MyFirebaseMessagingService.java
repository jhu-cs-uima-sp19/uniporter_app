package com.example.uniporter_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.example.uniporter_app.Authentication.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Testing: ";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            String messageString = remoteMessage.getData().get("message");
            Intent it = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), it, 0);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = ContextCompat.getColor(this, R.color.primary);

            NotificationManager mNotificationManager = (NotificationManager)
                    this.getSystemService(Context.NOTIFICATION_SERVICE);

            String CHANNEL_ID = "FCM_channel_01";
            CharSequence name = "Channel fCM";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mNotificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this,CHANNEL_ID)
                            .setContentTitle(getString(R.string.app_name))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(messageString))
                            .setSound(soundUri)
                            .setColor(color)
                            .setAutoCancel(true)
                            .setVibrate(new long[]{1000, 1000})
                            .setContentText(messageString);

            mBuilder.setContentIntent(contentIntent);
            Notification notification = mBuilder.build();
            mNotificationManager.notify(0, notification);
        }
    }
}