package com.alwayzcurious.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alwayzcurious.todo.Extras.DatabaseManager;
import com.alwayzcurious.todo.Extras.Task;

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

    String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        databaseManager = new DatabaseManager(this);
        String id=  getIntent().getStringExtra("id");


        Task task1 = databaseManager.getTaskById(id);
        Log.e("ViewTask","id = "+id +" task=");

        mTvDate = (TextView) findViewById(R.id.textViewDate);
        mTvTime = (TextView) findViewById(R.id.textViewTime);
        mTvLocation = (TextView) findViewById(R.id.textViewLocation);
        taskBackground = (ImageView) findViewById(R.id.imageView_taskBackgroundImage);

        taskTitle = (TextView) findViewById(R.id.editTextView_taskTitle);
        taskDescription = (TextView) findViewById(R.id.editText_taskDescription);
        preTaskRepFreq = (TextView) findViewById(R.id.editTex_preTaskRepFreq);
        preTaskRepFreqMin =(TextView) findViewById(R.id.edittext_interval);


        mTvDate.setText(String.format(Locale.ENGLISH, "%s, %s %02d", getDay(task1.getDateDay()), getMonth(task1.getDateMonth()), task1.getDateYear()));
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
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseManager.deleteTask(taskId);
                        Toast.makeText(ViewTask.this,"Task Deleted",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

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
}