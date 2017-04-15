package com.alwayzcurious.todo;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

import com.alwayzcurious.todo.Extras.DatabaseManager;
import com.alwayzcurious.todo.Extras.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vivek on 4/11/2017.
 */

public class AlarmReschedulerService extends IntentService {

    DatabaseManager databaseManager;
    Calendar calendar;

    public AlarmReschedulerService() {
        super("TODO_scheduler");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        databaseManager= new DatabaseManager(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            
        List<Task> taskList;
            calendar = Calendar.getInstance();
            taskList= databaseManager.getTodayTasks(calendar.get(Calendar.DAY_OF_MONTH),
                   calendar.get(Calendar.MONTH),
                   calendar.get(Calendar.YEAR),
                   calendar.get(Calendar.HOUR),
                   calendar.get(Calendar.MINUTE),
                   calendar.get(Calendar.SECOND));
        for (int i=0;i<taskList.size();i++)
        {
            //TODO schedule AlarmManager in Intent Service

            Intent todo = new Intent();
            todo.putExtra("identity",taskList.get(i).getIdentity());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),99,todo,PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar pendingDate= Calendar.getInstance();
            pendingDate.set(taskList.get(i).getDateYear(),
                    taskList.get(i).getDateMonth(),
                    taskList.get(i).getDateDay(),
                    taskList.get(i).getTimeHr(),
                    taskList.get(i).getTimeMin(),
                    taskList.get(i).getTimeSec());
            alarmManager.set(AlarmManager.RTC_WAKEUP,pendingDate.getTimeInMillis(),pendingIntent);

        }
    }
}
