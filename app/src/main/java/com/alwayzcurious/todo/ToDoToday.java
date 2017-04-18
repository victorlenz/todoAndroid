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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        mtvTaskCompleted = (TextView) findViewById(R.id.textView_taskCompleted);
        mTvTaskPending = (TextView) findViewById(R.id.textView_taskPending);
        date =(TextView) findViewById(R.id.textView_dateSwitch);

        mtvTaskCompleted.setText(manager.getCompletedTask());
        mTvTaskPending.setText(manager.getPendingTask());
        date.setText(String.format(Locale.ENGLISH,"%02d - %02d - %d",todaysCal.get(Calendar.DAY_OF_MONTH),todaysCal.get(Calendar.MONTH)+1, todaysCal.get(Calendar.YEAR) ));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               ArrayList<Task> tasks = new ArrayList<Task>();
                tasks.add(0,(Task) view.getTag(R.id.textView_Body));
                Intent intent = new Intent(ToDoToday.this,ViewTask.class);
                intent.putParcelableArrayListExtra("task",tasks);
                startActivity(intent);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

}
