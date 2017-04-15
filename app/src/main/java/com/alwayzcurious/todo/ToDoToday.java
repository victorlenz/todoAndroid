package com.alwayzcurious.todo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.alwayzcurious.todo.Extras.DatabaseManager;
import com.alwayzcurious.todo.Extras.Task;

import java.util.Calendar;
import java.util.List;

public class ToDoToday extends AppCompatActivity {

    ListView listView;
    List<Task> taskList;
    DatabaseManager manager;
    Calendar calendar;
    TodoAdapter todoAdapter;


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

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

}
