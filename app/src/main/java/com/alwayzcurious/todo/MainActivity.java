package com.alwayzcurious.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alwayzcurious.todo.Extras.DatabaseManager;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button date,noToDO,dayName,todoCalendar;
    TextView greeting,monthYear;
    Calendar calendar;
    DatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = new DatabaseManager(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SpannableString s = new SpannableString("ToDo");
        s.setSpan(new TypefaceSpan(this,"Bariol_Regular.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance

        myToolbar.setTitle(s);
        myToolbar.setNavigationIcon(R.drawable.ic_options);
        myToolbar.setBackgroundColor(Color.WHITE);
        myToolbar.setTitleTextColor(Color.BLACK);

        SharedPreferences sharedPreferences = getSharedPreferences(SignUp.APP_SHARED_PREFERENCES,MODE_PRIVATE);

        if (!sharedPreferences.getBoolean("is_signed_in",false)) {
            startActivity(new Intent(this,SignUp.class));
            finish();
        }

        date = (Button) findViewById(R.id.button_date);
        noToDO =(Button) findViewById(R.id.button_no_of_todo);
        dayName = (Button) findViewById(R.id.button_day);
        todoCalendar =(Button) findViewById(R.id.button_view_calendar);
        todoCalendar.setOnClickListener(this);
        greeting =(TextView) findViewById(R.id.textView_greeting);
        monthYear =(TextView) findViewById(R.id.textView_month_and_year);

        calendar = Calendar.getInstance();

        greeting.setText(getGreetingString());

        date.setText(String.format(Locale.ENGLISH,"%d",calendar.get(Calendar.DAY_OF_MONTH)));
        noToDO.setText(databaseManager.getThisDayTaskCount());

        dayName.setText(getCalenderString(calendar).day);
        monthYear.setText(String.format(Locale.ENGLISH,"%s %d",getCalenderString(calendar).month,calendar.get(Calendar.YEAR)));


    }

     class Date1{
        public String day,month;
    }
    Date1 getCalenderString(Calendar calendar)
    {


        Date1 date = new Date1();

        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.MONDAY : date.day ="MON"; break;
            case Calendar.TUESDAY : date.day ="TUE"; break;
            case Calendar.WEDNESDAY : date.day ="WED"; break;
            case Calendar.THURSDAY : date.day ="THU"; break;
            case Calendar.FRIDAY : date.day ="FRI"; break;
            case Calendar.SATURDAY : date.day ="SAT"; break;
            case Calendar.SUNDAY : date.day ="SUN"; break;
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


    public Bitmap createCircleBitmap(Bitmap bitmapimg){
        Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(),
                bitmapimg.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmapimg.getWidth(),
                bitmapimg.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmapimg.getWidth() / 2,
                bitmapimg.getHeight() / 2, bitmapimg.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case  R.id.button_view_calendar:startActivity(new Intent(this, ToDoToday.class)); break;
        }

    }

    private String getGreetingString(){
        Calendar calendar = Calendar.getInstance();

        if((calendar.get(Calendar.HOUR_OF_DAY)) < 12) {
            return "Good Morning";
        }
        else if((calendar.get(Calendar.HOUR_OF_DAY)) > 12 || (calendar.get(Calendar.HOUR_OF_DAY)) == 12 )
            return "Good Afternoon";
        else if((calendar.get(Calendar.HOUR_OF_DAY)) ==15 || (calendar.get(Calendar.HOUR_OF_DAY))  > 15 )
            return "Good Evening";
        else    return "Welcome";


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(R.id.action_add == item.getItemId())
            startActivity(new Intent(MainActivity.this,CreateTask.class));

        return true;
    }

    @Override
    protected void onResume() {

        noToDO.setText(databaseManager.getThisDayTaskCount());
        super.onResume();
    }
}
