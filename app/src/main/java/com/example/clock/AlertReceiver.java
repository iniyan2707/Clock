package com.example.clock;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.RadioButton;

import androidx.core.app.NotificationCompat;

import static com.example.clock.MainActivity.ans;

public class AlertReceiver extends BroadcastReceiver {
    public static boolean mediaplaying;
    public static Ringtone r;
    public static MediaPlayer mediaPlayer;
    public static boolean received;

    @Override
    public void onReceive(Context context, Intent intent) {
        received=true;

        if(ans==1){
            mediaPlayer = MediaPlayer.create(context, R.raw.sound1);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        else if(ans==2)
        {
             mediaPlayer = MediaPlayer.create(context, R.raw.sound2);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        else if(ans==3)
        {
            mediaPlayer = MediaPlayer.create(context, R.raw.sound3);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        else
        {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mediaPlayer = MediaPlayer.create(context, notification);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }



        NotificationHelper notificationHelper=new NotificationHelper(context);
        NotificationCompat.Builder nb=notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1,nb.build());


    }
}
