package com.alwayzcurious.todo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alwayzcurious.todo.Extras.DatabaseManager;
import com.alwayzcurious.todo.Extras.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ToDoToday extends AppCompatActivity {

    ListView listView;
    List<Task> taskList;
    DatabaseManager manager;
    Calendar calendar,todaysCal;
    TodoAdapter todoAdapter;
    TextView mtvTaskCompleted,mTvTaskPending,date;
    boolean flag=false;
    ImageView nextDay,prevDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_today);
        manager = new DatabaseManager(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SpannableString s = new SpannableString("Overview");
        s.setSpan(new TypefaceSpan(this,"Bariol_Regular.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance

        myToolbar.setTitle(s);
        myToolbar.setNavigationIcon(R.drawable.ic_options);
        myToolbar.setBackgroundColor(Color.WHITE);
        myToolbar.setTitleTextColor(Color.BLACK);

        nextDay = (ImageView) findViewById(R.id.imageButton_nextDay);
        prevDay = (ImageView) findViewById(R.id.imageButton_prevDay);

        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
              //  taskList=null;
               // listView.setAdapter(null);
                taskList = manager.getThisDayTask(calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND));
                Log.d("TODO","lenth of task "+taskList.size());
                todoAdapter = new TodoAdapter(ToDoToday.this,taskList);
                listView.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
                date.setText(String.format(Locale.ENGLISH,"%02d - %02d - %d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR) ));
            }
        });

        prevDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DAY_OF_MONTH,-1);
             //   taskList =null;
             //   listView.setAdapter(null);
                taskList = manager.getThisDayTask(calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND));
                Log.d("TODO","lenth of task "+taskList.size());
                todoAdapter = new TodoAdapter(ToDoToday.this,taskList);
                listView.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
                date.setText(String.format(Locale.ENGLISH,"%02d - %02d - %d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR) ));
            }
        });

        todaysCal = Calendar.getInstance();
        calendar = Calendar.getInstance();
        taskList = manager.getThisDayTask(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND));
        todoAdapter = new TodoAdapter(this,taskList);
        listView = (ListView) findViewById(R.id.todo_listView_details);
        listView.setAdapter(todoAdapter);
        todoAdapter.notifyDataSetChanged();
        flag = false;
        mtvTaskCompleted = (TextView) findViewById(R.id.textView_taskCompleted);
        mTvTaskPending = (TextView) findViewById(R.id.textView_taskPending);
        date =(TextView) findViewById(R.id.textView_dateSwitch);

        mtvTaskCompleted.setText(manager.getCompletedTask());
        mTvTaskPending.setText(manager.getPendingTask());
        date.setText(String.format(Locale.ENGLISH,"%02d - %02d - %d",todaysCal.get(Calendar.DAY_OF_MONTH),todaysCal.get(Calendar.MONTH)+1, todaysCal.get(Calendar.YEAR) ));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent t=  new Intent(ToDoToday.this,ViewTask.class);
                t.putExtra("id",((TextView)(view.findViewById(R.id.event_id))).getText().toString());
                startActivity(t);
                finish();

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        manager = new DatabaseManager(this);
        mtvTaskCompleted.setText(manager.getCompletedTask());
        mTvTaskPending.setText(manager.getPendingTask());
       if (flag) {
           taskList.clear();
           taskList = manager.getThisDayTask(calendar.get(Calendar.DAY_OF_MONTH),
                   calendar.get(Calendar.MONTH),
                   calendar.get(Calendar.YEAR),
                   calendar.get(Calendar.HOUR_OF_DAY),
                   calendar.get(Calendar.MINUTE),
                   calendar.get(Calendar.SECOND));
       }
        todoAdapter = new TodoAdapter(ToDoToday.this,taskList);
        todoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag=true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(R.id.action_add == item.getItemId()) {
            startActivity(new Intent(ToDoToday.this, CreateTask.class));
            finish();
        }
        else if(R.id.action_favorite == item.getItemId())
            startActivity(new Intent(ToDoToday.this,About.class));
        return true;


    }

}
