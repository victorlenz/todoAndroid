package com.alwayzcurious.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwayzcurious.todo.Extras.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ViewTask extends AppCompatActivity {

    TextView mTvDate, mTvTime, mTvLocation;
    TextView taskTitle;
    TextView taskDescription;
    TextView isPreTaskRep;
    ImageView taskBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        ArrayList<Task> task = new ArrayList<>();
        task.add((Task)getIntent().getParcelableExtra("task"));
        Task task1 = new Task();
        task1= task.get(0);

        mTvDate = (TextView) findViewById(R.id.textViewDate);
        mTvTime = (TextView) findViewById(R.id.textViewTime);
        mTvLocation = (TextView) findViewById(R.id.textViewLocation);
        taskBackground = (ImageView) findViewById(R.id.imageView_taskBackgroundImage);

        taskTitle = (TextView) findViewById(R.id.editTextView_taskTitle);
        taskDescription = (TextView) findViewById(R.id.editText_taskDescription);
        isPreTaskRep = (TextView) findViewById(R.id.editTex_preTaskRep);

        mTvDate.setText(String.format(Locale.ENGLISH, "%s, %s %02d", getDay(task1.getDateDay()), getMonth(task1.getDateMonth()), task1.getDateYear()));
        mTvTime.setText(String.format(Locale.ENGLISH,"%02d :%02d",task1.getTimeHr(),task1.getTimeMin()));
        mTvLocation.setText(task1.getLocation());
        taskBackground.setImageURI(null);

        taskTitle.setText(task1.getTitle());
        taskDescription.setText(task1.getDescription());



    }


    String getDay(int day) {

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

    String getMonth(int month) {

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

}