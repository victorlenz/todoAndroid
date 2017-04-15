package com.alwayzcurious.todo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alwayzcurious.todo.Extras.DatabaseManager;
import com.alwayzcurious.todo.Extras.Task;
import com.bumptech.glide.load.resource.bitmap.BitmapDecoder;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class CreateTask extends AppCompatActivity implements View.OnClickListener {

    TextView mTvDate,mTvTime,mTvLocation;
    EditText taskTitle,taskDescription,isPreTaskRep;
    ImageView taskBackground;
    AlarmManager alarmManager;
    Calendar taskCalendar,tempTaskCalendar;
    SimpleDateFormat simpleDateFormat;
    static  String MY_TODO_REMINDER_INTENT_ACTION = "com.alwayzcurious.todo.reminder.";

    DatabaseManager databaseManager = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SpannableString s = new SpannableString("Create New Task");
        s.setSpan(new TypefaceSpan(this,"Bariol_Regular.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        taskCalendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SS",Locale.ENGLISH);

        // Update the action bar title with the TypefaceSpan instance

        myToolbar.setTitle(s);
        myToolbar.setNavigationIcon(R.drawable.ic_options);
        myToolbar.setBackgroundColor(Color.WHITE);
        myToolbar.setTitleTextColor(Color.BLACK);

        mTvDate = (TextView) findViewById(R.id.textViewDate);
        mTvTime = (TextView) findViewById(R.id.textViewTime);
        mTvLocation = (TextView) findViewById(R.id.textViewLocation);
        taskBackground = (ImageView) findViewById(R.id.imageView_taskBackgroundImage);

        taskTitle = (EditText) findViewById(R.id.editTextView_taskTitle);
        taskDescription = (EditText) findViewById(R.id.editText_taskDescription);
        isPreTaskRep = (EditText) findViewById(R.id.editTex_preTaskRep);

        findViewById(R.id.imageButtonSetData).setOnClickListener(this);
        findViewById(R.id.imageButtonSetTime).setOnClickListener(this);
        findViewById(R.id.imageButtonCreateTask).setOnClickListener(this);
        findViewById(R.id.ll1_taskBackgroundImage).setBackgroundColor(Color.argb(0,0,0,0));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_task,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_pick_image_fortask)
        {
            Intent t = new Intent();
            t.setType("image/*");
            t.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(t,999);
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == 999)
            {

                    taskBackground.setImageURI(data.getData());
                    findViewById(R.id.ll1_taskBackgroundImage).setBackgroundColor(Color.argb(0,0,0,0));
                    //saveImage()
                Log.d("TODO","image set "+data.getData());
            }
        }
        else {
            Toast.makeText(this,"RESULT FALSE",Toast.LENGTH_LONG).show();


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        final Calendar c1 = Calendar.getInstance();

        switch (view.getId())
        {
            case R.id.imageButtonSetData : {



                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTask.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                if(taskCalendar!=null)
                                    tempTaskCalendar = (Calendar) taskCalendar.clone();
                                else
                                    tempTaskCalendar = Calendar.getInstance();


                                tempTaskCalendar.set(Calendar.YEAR,year);
                                tempTaskCalendar.set(Calendar.MONTH,month);
                                tempTaskCalendar.set(Calendar.DAY_OF_MONTH,day);


                                Log.d( "TODO","date test "+ simpleDateFormat.format(tempTaskCalendar.getTimeInMillis()));
                                Log.d("TODO","calendar ="+tempTaskCalendar.get(Calendar.HOUR_OF_DAY)+" "+tempTaskCalendar.get(Calendar.MINUTE)
                                +","+getCalenderString(tempTaskCalendar).month +" "+getCalenderString(tempTaskCalendar).day);

                                mTvDate.setText(String.format(Locale.ENGLISH,"%s %s %02d",getCalenderString(tempTaskCalendar).day, getCalenderString(tempTaskCalendar).month,day));
                            }
                        },c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH),
                        c1.get(Calendar.DAY_OF_MONTH));


                datePickerDialog.show();
            }

             break;

            case R.id.imageButtonSetTime : {

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        mTvTime.setText(String.valueOf(i)+":"+String.valueOf(i1) + String.valueOf( (c1.get(Calendar.AM_PM)) == Calendar.PM ? "PM":"AM"));

                        if(tempTaskCalendar!=null)
                            taskCalendar = (Calendar) tempTaskCalendar.clone();
                        else
                            taskCalendar = Calendar.getInstance();


                        taskCalendar.set(Calendar.HOUR_OF_DAY,i);
                        taskCalendar.set(Calendar.MINUTE,i1);
                        taskCalendar.set(Calendar.SECOND,0);
                        taskCalendar.set(Calendar.MILLISECOND,0);


                        Log.d( "TODO","date test "+ simpleDateFormat.format(taskCalendar.getTimeInMillis()));

                        Log.d("TODO","calendar ="+taskCalendar.get(Calendar.HOUR_OF_DAY)+" "+taskCalendar.get(Calendar.MINUTE)
                                +","+getCalenderString(taskCalendar).month +" "+getCalenderString(taskCalendar).day);

                    }
                },c1.get(Calendar.HOUR_OF_DAY),c1.get(Calendar.MINUTE),false);



                timePickerDialog.show();



            } break;

            case R.id.imageButtonCreateTask :{

                Intent intent = new Intent(CreateTask.this,MyReceiver.class);
                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
               // intent.setAction(MY_TODO_REMINDER_INTENT_ACTION+"1");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(CreateTask.this,99,intent,0);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                //TODO fix the bug millisecond problem


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,taskCalendar.getTimeInMillis(),pendingIntent);
                }else  alarmManager.set(AlarmManager.RTC_WAKEUP,taskCalendar.getTimeInMillis(),pendingIntent);
                Log.d( "TODO","date test set "+ simpleDateFormat.format(taskCalendar.getTimeInMillis()));


                Log.d("TODO","calendar ="+taskCalendar.get(Calendar.HOUR_OF_DAY)+" "+taskCalendar.get(Calendar.MINUTE)
                        +","+getCalenderString(taskCalendar).month +" "+getCalenderString(taskCalendar).day);
                Log.d("TODO", (""+ alarmManager.getNextAlarmClock().getTriggerTime()));
                Log.d( "TODO","date test 1"+ simpleDateFormat.format(alarmManager.getNextAlarmClock().getTriggerTime()));
                Toast.makeText(this,"alarm startet@",Toast.LENGTH_LONG).show();

                //TODO apply validations

                Task task = new Task();

                task.setDateDay(taskCalendar.get(Calendar.DAY_OF_MONTH));
                task.setDateMonth(taskCalendar.get(Calendar.MONTH));
                task.setDateYear(taskCalendar.get(Calendar.YEAR));
                task.setTimeHr(taskCalendar.get(Calendar.HOUR));
                task.setTimeMin(taskCalendar.get(Calendar.MINUTE));
                task.setTimeSec(taskCalendar.get(Calendar.SECOND));
                task.setTitle(taskTitle.getText().toString());
                task.setPreTaskRepetition((Integer.valueOf(isPreTaskRep.getText().toString())));
                task.setIdentity(generateIdentity());
                task.setPostTaskRepetition(0);
                task.setIsPostTaskRepition(false);

                if(!isPreTaskRep.getText().toString().equals("0")){
                    task.setDescription(taskDescription.getText().toString());
                    task.setIsPreTaskRepetiton(true);
                }
                else task.setIsPreTaskRepetiton(false);

                databaseManager.createTask(task);
                finish();

            }
        }


    }


    class Date1{
        public String day,month;
    }
   Date1 getCalenderString(Calendar calendar)
    {

       Date1 date = new Date1();

        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.MONDAY : date.day ="Mon"; break;
            case Calendar.TUESDAY : date.day ="Tue"; break;
            case Calendar.WEDNESDAY : date.day ="Wed"; break;
            case Calendar.THURSDAY : date.day ="Thu"; break;
            case Calendar.FRIDAY : date.day ="Fri"; break;
            case Calendar.SATURDAY : date.day ="Sat"; break;
            case Calendar.SUNDAY : date.day ="Sun"; break;
        }

        switch (calendar.get(Calendar.MONTH))
        {
            case Calendar.JANUARY : date.month ="January"; break;
            case Calendar.FEBRUARY : date.month ="February"; break;
            case Calendar.MARCH : date.month ="March"; break;
            case Calendar.APRIL : date.month ="April"; break;
            case Calendar.JUNE : date.month ="June"; break;
            case Calendar.JULY : date.month ="July"; break;
            case Calendar.AUGUST : date.month ="August"; break;
            case Calendar.SEPTEMBER : date.month ="September"; break;
            case Calendar.OCTOBER : date.month ="October"; break;
            case Calendar.NOVEMBER : date.month ="November"; break;
            case Calendar.DECEMBER : date.month ="December"; break;
            case Calendar.MAY : date.month ="May"; break;
        }



        return  date;

    }

    class CustomTime{

        int day,year,hour,month,minutes;

    }

    public String generateIdentity() {
        byte[] array = new byte[10]; // length is bounded by 7
        new Random().nextBytes(array);

        return new String(array, Charset.forName("UTF-8"));

    }
}
