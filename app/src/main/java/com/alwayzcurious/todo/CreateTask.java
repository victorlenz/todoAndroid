package com.alwayzcurious.todo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;

public class CreateTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SpannableString s = new SpannableString("Create New Task");
        s.setSpan(new TypefaceSpan(this,"Bariol_Regular.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance

        myToolbar.setTitle(s);
        myToolbar.setNavigationIcon(R.drawable.ic_options);
        myToolbar.setBackgroundColor(Color.WHITE);
        myToolbar.setTitleTextColor(Color.BLACK);
    }
}
