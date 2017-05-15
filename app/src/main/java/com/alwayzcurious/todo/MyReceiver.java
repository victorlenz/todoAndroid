package com.alwayzcurious.todo;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.alwayzcurious.todo.Extras.MyWakeLock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by vivek on 4/9/2017.
 */

public class MyReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MyWakeLock.acquire(context);

        Log.d( "TODO", "Broadcast"+intent.getAction());

     if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
        {
            startWakefulService(context,new Intent(context, AlarmReschedulerService.class));
        }else
        {


            ComponentName comp = new ComponentName(context.getPackageName(),
                    NotificationService.class.getName());
            startWakefulService(context, (intent.setComponent(comp)));
            setResultCode(Activity.RESULT_OK);

            SimpleDateFormat  simpleDateFormat= new SimpleDateFormat("HH:mm:ss:SS", Locale.ENGLISH);
            Calendar taskCalendar = Calendar.getInstance();
            Log.d( "TODO","date test "+ simpleDateFormat.format(taskCalendar.getTimeInMillis()));

            MyWakeLock.release();
        }

        //set Activity
       /* AlarmActivity inst = AlarmActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");*/

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)



    }
    private void sendNotification(String msg, Context context) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        NotificationManager alarmNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                context).setContentTitle("Alarm").setSmallIcon(R.drawable.ic_action_add)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);


        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}
