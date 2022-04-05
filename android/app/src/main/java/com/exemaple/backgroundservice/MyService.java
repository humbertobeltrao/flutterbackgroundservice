package com.exemaple.backgroundservice;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private MediaPlayer mediaPlayer;
    private static final int NOTIFICATION_ID = 1;
    private int mStartId;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.badnews);

        if(mediaPlayer != null) {
            mediaPlayer.setLooping(false);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopSelf(NOTIFICATION_ID);
                }
            });
        }
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, intent, 0);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, "messages")
                    .setContentTitle("Flutter background music")
                    .setContentText("Está tocando a música em background")
                    .setSmallIcon(R.drawable.launch_background);

            startForeground(NOTIFICATION_ID, builder.build());

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(mediaPlayer != null) {
            mStartId = startId;

            mediaPlayer.start();
        }

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
