package com.example.clock;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    public static Ringtone r;

    @Override
    public void onReceive(Context context, Intent intent) {

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
             r = RingtoneManager.getRingtone(context, notification);
            r.play();

        NotificationHelper notificationHelper=new NotificationHelper(context);
        NotificationCompat.Builder nb=notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1,nb.build());


    }
}
