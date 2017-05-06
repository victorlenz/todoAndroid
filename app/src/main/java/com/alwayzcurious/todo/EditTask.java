package com.alwayzcurious.todo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alwayzcurious.todo.Extras.DatabaseManager;
import com.alwayzcurious.todo.Extras.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.alwayzcurious.todo.ViewTask.getDay;
import static com.alwayzcurious.todo.ViewTask.getMonth;

public class EditTask extends AppCompatActivity implements View.OnClickListener{


    TextView mTvDate,mTvTime,mTvLocation;
    EditText taskTitle,taskDescription, preTaskRepFreq,preTaskRepFreqMin;
    ImageView taskBackground,editTask;

    AlarmManager alarmManager;
    Calendar taskCalendar,tempTaskCalendar;
    SimpleDateFormat simpleDateFormat;
    Task task1;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        databaseManager = new DatabaseManager(this);

        mTvDate = (TextView) findViewById(R.id.textViewDate);
        mTvTime = (TextView) findViewById(R.id.textViewTime);
        mTvLocation = (TextView) findViewById(R.id.textViewLocation);
        taskBackground = (ImageView) findViewById(R.id.imageView_taskBackgroundImage);

        taskTitle = (EditText) findViewById(R.id.editTextView_taskTitle);
        taskDescription = (EditText) findViewById(R.id.editText_taskDescription);
        preTaskRepFreq = (EditText) findViewById(R.id.editTex_preTaskFreq);
        preTaskRepFreqMin =(EditText) findViewById(R.id.edittext_preTaskinterval);

        task1= databaseManager.getTaskById(getIntent().getStringExtra("taskId"));
        mTvDate.setText(String.format(Locale.ENGLISH, "%s, %s %02d", getDay(task1.getDateDay()), getMonth(task1.getDateMonth()), task1.getDateDay()));
        mTvTime.setText(String.format(Locale.ENGLISH,"%02d :%02d",task1.getTimeHr(),task1.getTimeMin()));
        mTvLocation.setText(task1.getLocation());
        taskBackground.setImageURI(null);

        editTask =(ImageButton) findViewById(R.id.imageButtonCreateTask);
        editTask.setOnClickListener(this);

        taskTitle.setText(task1.getTitle());
        taskDescription.setText(task1.getDescription());

        findViewById(R.id.imageButtonSetData).setOnClickListener(this);
        findViewById(R.id.imageButtonSetTime).setOnClickListener(this);
        findViewById(R.id.imageButtonCreateTask).setOnClickListener(this);

        Log.d("ViewTask","Task=>"+task1.getPreTaskFrequency() +" total task=>"+ databaseManager.getTotalTaskCount());
        Log.d("ViewTask","Task=>"+task1.getPreTaskRepetitionMinutes());

        mTvLocation.setText(task1.getLocation());

        preTaskRepFreq.setText(String.valueOf(task1.getPreTaskFrequency()));
        preTaskRepFreqMin.setText(String.valueOf(task1.getPreTaskRepetitionMinutes()));

        taskCalendar = Calendar.getInstance();
        taskCalendar.set(task1.getDateYear(),task1.getDateMonth(),task1.getDateDay(),task1.getTimeHr(),task1.getTimeMin(),task1.getTimeSec());
        tempTaskCalendar = (Calendar) taskCalendar.clone();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        final Calendar c1 = Calendar.getInstance();

        switch (view.getId())
        {
            case R.id.imageButtonSetData : {



                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTask.this,
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


                                mTvDate.setText(String.format(Locale.ENGLISH,"%s %s %02d",getCalenderString(tempTaskCalendar).day, getCalenderString(tempTaskCalendar).month,day));
                            }
                        },c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH),
                        c1.get(Calendar.DAY_OF_MONTH));


                datePickerDialog.show();
            }

            break;

            case R.id.imageButtonSetTime : {

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditTask.this, new TimePickerDialog.OnTimeSetListener() {
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


                        /*Log.d( "tTODO","date test "+ simpleDateFormat.format(taskCalendar.getTimeInMillis()));

                        Log.d("tTODO","calendar ="+taskCalendar.get(Calendar.HOUR_OF_DAY)+" "+taskCalendar.get(Calendar.MINUTE)
                                +","+getCalenderString(taskCalendar).month +" "+getCalenderString(taskCalendar).day);*/

                    }
                },c1.get(Calendar.HOUR_OF_DAY),c1.get(Calendar.MINUTE),false);



                timePickerDialog.show();



            } break;

            case R.id.imageButtonCreateTask :{



                if (!validateInput())
                {
                    AlertDialog.Builder dialogue= new AlertDialog.Builder(EditTask.this);
                    dialogue.setMessage("Title & Description can't be less than 4 character and can't contain special character");
                    dialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialogue.show();
                    return;
                }

                if(mTvLocation.getText().length() ==0)
                {
                    mTvLocation.setText("Not Available");
                }

                if(preTaskRepFreq.getText().toString().length() ==0 )
                    preTaskRepFreq.setText("0");
                if(preTaskRepFreqMin.getText().toString().length() ==0)
                    preTaskRepFreqMin.setText("0");




                //TODO fix the bug millisecond problem

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
                    PendingIntent pendingIntentforCancel = PendingIntent.getBroadcast(EditTask.this,seedIdentity+i, intentCancel,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntentforCancel);
                    Toast.makeText(EditTask.this,"cancelled@"+(seedIdentity+i),Toast.LENGTH_SHORT).show();
                }


                //TODO apply validations

                Task task = new Task();

                task.setDateDay(taskCalendar.get(Calendar.DAY_OF_MONTH));
                task.setDateMonth(taskCalendar.get(Calendar.MONTH));
                task.setDateYear(taskCalendar.get(Calendar.YEAR));
                task.setTimeHr(taskCalendar.get(Calendar.HOUR));
                task.setTimeMin(taskCalendar.get(Calendar.MINUTE));
                task.setTimeSec(taskCalendar.get(Calendar.SECOND));
                task.setTitle(taskTitle.getText().toString());
                task.setLocation(mTvLocation.getText().toString());
                task.setDescription(taskDescription.getText().toString());
                task.setIdentity(CreateTask.generateIdentity());
               //TODO do this
               // task.setIdentity(generateIdentity());


                if(!preTaskRepFreq.getText().toString().equals("0")){
                    task.setPreTaskRepetitionMinutes(Integer.parseInt(preTaskRepFreqMin.getText().toString()));
                    task.setPreTaskFrequency(Integer.parseInt(preTaskRepFreq.getText().toString()));
                }
                else {
                    task.setPreTaskRepetitionMinutes(0);
                    task.setPreTaskFrequency(0);
                }

                databaseManager.deleteTask(getIntent().getStringExtra("taskId"));
                String id= databaseManager.createTask(task);

                Intent intent = new Intent(EditTask.this,MyReceiver.class);
                intent.putExtra("id",id);
                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                // intent.setAction(MY_TODO_REMINDER_INTENT_ACTION+"1");


                 x= String.valueOf(task.getId()).length();
                 seedIdentity = Integer.parseInt(task.getIdentity());
                while (x--!=0)
                {
                    seedIdentity*=10;
                }
                seedIdentity = Integer.parseInt(id)+seedIdentity;


                for(int i=0;i<Integer.parseInt(preTaskRepFreq.getText().toString());i++)
                {
                    Calendar calendar = (Calendar) taskCalendar.clone();
                    calendar.add(Calendar.MINUTE, - Integer.parseInt(preTaskRepFreqMin.getText().toString()));
                    PendingIntent pendingIntentRep = PendingIntent.getBroadcast(EditTask.this,seedIdentity+i,intent,0);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntentRep);
                    Toast.makeText(this,"alarm started@"+(seedIdentity+i),Toast.LENGTH_SHORT).show();
                }

               /*
                PendingIntent pendingIntent = PendingIntent.getBroadcast(EditTask.this,Integer.parseInt(id),intent,0);
                alarmManager.set(AlarmManager.RTC_WAKEUP,taskCalendar.getTimeInMillis(),pendingIntent);
*/
                finish();

            }
        }


    }

    private boolean validateInput() {

        if( taskTitle.getText().toString().length() >3 &&
                taskDescription.getText().toString().length()>3 &&

                taskTitle.getText().toString().matches("^[a-zA-Z]{4,}$")
                && taskDescription.getText().toString().matches("^[a-zA-Z]{4,}$")

                )
            return true;

        return false;

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
}
