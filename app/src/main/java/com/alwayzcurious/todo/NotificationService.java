package com.alwayzcurious.todo;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alwayzcurious.todo.Extras.DatabaseManager;
import com.alwayzcurious.todo.Extras.Task;

/**
 * Created by vivek on 4/9/2017.
 */

public class NotificationService extends IntentService {

    public NotificationService() {
        super("com.alwayzcurious.todo.service");
    }

    DatabaseManager databaseManager;Ringtone ringtone;

    @Override
    protected void onHandleIntent(Intent intent) {

        databaseManager = new DatabaseManager(getApplicationContext());
        Log.d("TODO","Notify service got id= "+ intent.getStringExtra("id"));
       sendNotification(intent.getStringExtra("id"));
        Log.d("TODO","starting notification");

    }

    private void sendNotification(String id) {

        Task task = databaseManager.getTaskById(id);
        String msg = task.getDescription(),title = task.getTitle();




        Log.d("AlarmService", "Preparing to send notification...: " + msg);
         NotificationManager  alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this,ViewTask.class);
        intent1.putExtra("id",id);
        intent1.putExtra("launched",true);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        startActivity(intent1);

        Intent intent2 = new Intent(this,ViewTask.class);
        intent2.putExtra("id",id);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent2, 0);


        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle(title).setSmallIcon(R.drawable.logo)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg).setAutoCancel(true)
                ;

        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
