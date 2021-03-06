package com.alwayzcurious.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alwayzcurious.todo.Extras.DatabaseManager;
import com.alwayzcurious.todo.Extras.Task;
import com.alwayzcurious.todo.Extras.Validator;

import java.util.Calendar;
import java.util.Locale;

public class ViewTask extends AppCompatActivity implements View.OnClickListener {

    TextView mTvDate, mTvTime, mTvLocation;
    TextView taskTitle;
    TextView taskDescription;
    ImageView taskBackground;
    private TextView preTaskRepFreq;
    private TextView preTaskRepFreqMin;
    ImageButton editTask,markDone,deleteTask;
    DatabaseManager databaseManager;

    AlertDialog.Builder alertDialog;
    AlarmManager alarmManager;
    String taskId;
    Calendar calendar;
     Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        databaseManager = new DatabaseManager(this);
        String id=  getIntent().getStringExtra("id");


        if(getIntent().getBooleanExtra("launched",false))
        {
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

             ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
            ringtone.stop();
            ringtone.play();
        }

        Log.d("TODO","ViewTask id "+id);

        calendar = Calendar.getInstance();


        Task task1 = databaseManager.getTaskById(id);

        calendar.set(task1.getDateYear(),task1.getDateMonth(),task1.getDateDay(),task1.getTimeHr(),task1.getTimeMin());
        Log.e("ViewTask","id = "+id +" task=");

        mTvDate = (TextView) findViewById(R.id.textViewDate);
        mTvTime = (TextView) findViewById(R.id.textViewTime);
        mTvLocation = (TextView) findViewById(R.id.textViewLocation);
        taskBackground = (ImageView) findViewById(R.id.imageView_taskBackgroundImage);

        taskTitle = (TextView) findViewById(R.id.editTextView_taskTitle);
        taskDescription = (TextView) findViewById(R.id.editText_taskDescription);
        preTaskRepFreq = (TextView) findViewById(R.id.editTex_preTaskRepFreq);
        preTaskRepFreqMin =(TextView) findViewById(R.id.edittext_interval);

        Log.d("TODO","day ="+task1.getDateDay());

        mTvDate.setText(String.format(Locale.ENGLISH, "%s, %s %02d", getDay(calendar.get(Calendar.DAY_OF_WEEK)), getMonth(task1.getDateMonth()), task1.getDateYear()));
        mTvTime.setText(String.format(Locale.ENGLISH,"%02d :%02d",task1.getTimeHr(),task1.getTimeMin()));
        mTvLocation.setText(task1.getLocation());
        taskBackground.setImageURI(null);

        editTask = (ImageButton) findViewById(R.id.button_editTask);
        markDone = (ImageButton) findViewById(R.id.button_markDone);
        deleteTask =(ImageButton) findViewById(R.id.button_deleteTask);


        taskTitle.setText(task1.getTitle());
        taskDescription.setText(task1.getDescription());

        taskId= String.valueOf(task1.getId());
        Log.d("ViewTask","Task=>"+task1.getPreTaskFrequency() +" total task=>"+ databaseManager.getTotalTaskCount());
        Log.d("ViewTask","Task=>"+task1.getPreTaskRepetitionMinutes());

        mTvLocation.setText(task1.getLocation());

        preTaskRepFreq.setText(String.valueOf(task1.getPreTaskFrequency()));
        preTaskRepFreqMin.setText(String.valueOf(task1.getPreTaskRepetitionMinutes()));

        alertDialog = new AlertDialog.Builder(ViewTask.this);

        editTask.setOnClickListener(this);
        markDone.setOnClickListener(this);
        deleteTask.setOnClickListener(this);

        findViewById(R.id.ll1_taskBackgroundImage).setBackgroundColor(Color.argb(0,0,0,0));
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(event.getAction() == KeyEvent.ACTION_DOWN) {

            if(keyCode== KeyEvent.KEYCODE_VOLUME_DOWN || keyCode==KeyEvent.KEYCODE_VOLUME_UP)
              try{
                  ringtone.stop();
              }catch (Exception e){}
            Log.i("ViewTask","stopping ring");
        }

        return  super.onKeyDown(keyCode,event);
    }

    static String getDay(int day) {

        switch (day) {
            case Calendar.MONDAY:
                return "Mon";
            case Calendar.TUESDAY:
                return "Tue";
            case Calendar.WEDNESDAY:
                return "Wed";
            case Calendar.THURSDAY:
                return "Thu";
            case Calendar.FRIDAY:
                return "Fri";
            case Calendar.SATURDAY:
                return "Sat";
            case Calendar.SUNDAY:
                return "Sun";
        }

        return null;

    }

    static String getMonth(int month) {

        switch (month) {
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return " August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
            case Calendar.MAY:
                return "May";
        }
        return null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.button_deleteTask : {

                Log.e("TODO", "button delete task");

                alertDialog.setMessage("Are You Sure to Delete?");
                alertDialog.setIcon(R.mipmap.ic_cross);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        DatabaseManager databaseManager = new DatabaseManager(ViewTask.this);
                        Task task1 = databaseManager.getTaskById(taskId);

                        int x= String.valueOf(task1.getId()).length();
                        int seedIdentity = Integer.parseInt(task1.getIdentity());
                        while (x-- !=0)
                        {
                            seedIdentity*=10;
                        }
                        seedIdentity = task1.getId()+seedIdentity;

                        for(int i=0;i<task1.getPreTaskFrequency();i++)
                        {
                            Intent intentCancel = new Intent();
                            intentCancel.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            intentCancel.putExtra("id",getIntent().getStringExtra("taskId"));
                            PendingIntent pendingIntentforCancel = PendingIntent.getBroadcast(ViewTask.this,seedIdentity+i, intentCancel,PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
                            alarmManager.cancel(pendingIntentforCancel);
                            Toast.makeText(ViewTask.this,"deleted@"+(seedIdentity+i),Toast.LENGTH_SHORT).show();
                        }

                        databaseManager.deleteTask(taskId);
                        Toast.makeText(ViewTask.this,"Task Deleted",Toast.LENGTH_LONG).show();
                        finish();
                    }


                });

               try{
                   ringtone.stop();
               }catch (Exception ignored){}
                alertDialog.setNegativeButton("No",null);

                alertDialog.show();

            } break;

            case R.id.button_editTask : {
                Log.e("TODO", "button delete task");
                Intent intent = new Intent(ViewTask.this,EditTask.class);
                intent.putExtra("taskId",taskId);
                startActivity(intent);
                finish();

            } break;

            case R.id.button_markDone : {
                Log.e("TODO", "button delete task");
                databaseManager.markDone(taskId);
                Toast.makeText(ViewTask.this,"Task Completed",Toast.LENGTH_LONG).show();
            } break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            ringtone.stop();
        }catch (Exception ignored){}
    }
}