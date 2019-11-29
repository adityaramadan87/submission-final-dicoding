package co.id.ramadanrizky.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import co.id.ramadanrizky.watchmovie.preferences.SharedPreferencesManager;
import co.id.ramadanrizky.watchmovie.receiver.DailyReceiver;
import co.id.ramadanrizky.watchmovie.receiver.ReleaseReceiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private Switch switch_daily, switch_release;
    private int notifId = 1;
    private int notifReleaseId = 0;
    private SharedPreferencesManager prefm;
    private ReleaseReceiver releaseReceiver = new ReleaseReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        prefm = new SharedPreferencesManager(this);
        switch_daily = findViewById(R.id.switch_daily);
        switch_release = findViewById(R.id.switch_daily_release);



        setChecked();
        listener();


    }

    private void setChecked() {
        //switch daily
        if (prefm.loadStateNotification() == true){
            switch_daily.setChecked(true);
        }else {
            switch_daily.setChecked(false);
        }

        //switch release
        if (prefm.loadStateNotificationRelease() == true){
            switch_release.setChecked(true);
        }else {
            switch_release.setChecked(false);
        }
    }



    private void listener() {

        switch_daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    prefm.saveStateNotificatio(true);
                    scheduleNotification();
                    Toast.makeText(SettingsActivity.this, getString(R.string.daily_reminder)+" is Activated", Toast.LENGTH_SHORT).show();
                }else {
                    prefm.saveStateNotificatio(false);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(getPendingDaily());
                    Toast.makeText(SettingsActivity.this, getString(R.string.daily_reminder)+" is Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        switch_release.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    prefm.saveStateNotificationRelease(true);
                    releaseReceiver.setAlarm(SettingsActivity.this, notifReleaseId);
                }else {
                    prefm.saveStateNotificationRelease(false);
                    releaseReceiver.cancelAlarm(SettingsActivity.this);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        notifReleaseId = 0;
    }

//    private void scheduleNotificationRelease() {
//        PendingIntent receiverReleasePending = getPendingRelease(notifReleaseId);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        final Calendar calendarRls = Calendar.getInstance();
//        calendarRls.set(Calendar.HOUR_OF_DAY, 8);
//        calendarRls.set(Calendar.MINUTE, 0);
//        calendarRls.set(Calendar.SECOND, 0);
//
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarRls.getTimeInMillis(), AlarmManager.INTERVAL_DAY, receiverReleasePending);
//        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendarRls.getTimeInMillis(), receiverReleasePending);
//        }
//    }

//    private PendingIntent getPendingRelease(int notifReleaseId) {
//        Intent receiverReleaseIntent = new Intent(this, ReleaseReceiver.class);
//        receiverReleaseIntent.putExtra(ReleaseReceiver.NOTIFICATION_RELEASE_ID, notifReleaseId);
//        return PendingIntent.getBroadcast(getApplicationContext(), 0, receiverReleaseIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//    }


    private void scheduleNotification() {
        PendingIntent receiverPending = getPendingDaily();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, receiverPending);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), receiverPending);
        }
    }

    private PendingIntent getPendingDaily() {
        Intent receiverIntent = new Intent(this, DailyReceiver.class);
        receiverIntent.putExtra(DailyReceiver.NOTIFICATION_ID, notifId);
        receiverIntent.putExtra(DailyReceiver.NOTIFICATION, getNotification(getString(R.string.daily_reminder), getString(R.string.daily_message)));
        return PendingIntent.getBroadcast(getApplicationContext(), 0, receiverIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private Notification getNotification(String tittle, String message) {
        String channel_id = "CHANNEL_1";
        String channel_name = "notif_daily";
        Intent notifIntent = new Intent(this, MainActivity.class);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0 , notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this,channel_id )
                .setContentTitle(tittle)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            channel.setShowBadge(true);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] {1000, 1000, 1000, 1000, 1000});
            channel.setLightColor(Color.BLUE);
            notifBuilder.setChannelId(channel_id);

            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = notifBuilder.build();
        return notification;
    }
}
