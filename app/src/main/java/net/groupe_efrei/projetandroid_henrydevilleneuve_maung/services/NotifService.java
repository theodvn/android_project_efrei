package net.groupe_efrei.projetandroid_henrydevilleneuve_maung.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;

import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.R;
import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.activities.ListMusicActivity;

import java.util.Timer;
import java.util.TimerTask;

public class NotifService extends Service {

    Timer timer;
    TimerTask timerTask;
    SharedPreferences sharedPreferences;
    public NotifService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timerTask.cancel();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        sharedPreferences = getApplicationContext().getSharedPreferences("EventMe", Context.MODE_PRIVATE);
        final Handler handler = new Handler();
        String fred = sharedPreferences.getString("frequence", "");
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        createNotification();
                    }});
            }};
        Long time = Long.parseLong(fred) * 60000;
        timer.schedule(timerTask, 0, time);

    }

    private final void createNotification(){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final Intent launchNotifiactionIntent = new Intent(this, ListMusicActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker("Event Notif")
                .setSmallIcon(R.drawable.ic_favorite_border_white_24dp)
                .setContentTitle("Nouveautées")
                .setContentText("Vérifiez si de nouveaux évènements sont disponibles !")
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000});

        mNotification.notify(10, builder.build());
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
