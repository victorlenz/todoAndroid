package com.alwayzcurious.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwayzcurious.todo.Extras.Task;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by vivek on 4/15/2017.
 */

public class TodoAdapter extends BaseAdapter {

    Context mContext;
    List<Task> taskList;
    Calendar calendar;
    TodoAdapter(Context context, List<Task> taskList)
    {
        mContext = context; this.taskList =taskList;
        calendar =  Calendar.getInstance();

    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View MyView=view;
        ViewHolder viewHolder=null;
        if(MyView ==null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            MyView = inflater.inflate(R.layout.todo_today_lisview_row,viewGroup,false);
            viewHolder = new ViewHolder(MyView);
        }

        Task task = taskList.get(i);

        calendar.set(task.getDateYear(),task.getDateMonth(),task.getDateDay(),task.getTimeHr(),task.getTimeMin(),task.getTimeSec());

        viewHolder.time.setText(String.format(Locale.ENGLISH,"%02d : %02d",calendar.get(Calendar.HOUR),task.getTimeMin()));
        viewHolder.ampm.setText((calendar.get(Calendar.AM_PM))==Calendar.PM?"PM":"AM");
        viewHolder.title.setText(String.format(Locale.ENGLISH,"%s %s",getCalenderString(calendar).day, getCalenderString(calendar).month));
        viewHolder.body.setText(task.getTitle());

        return null;
    }


    class ViewHolder
    {
        TextView title,time,body,ampm;
        ImageView color;

        ViewHolder(View v)
        {
            time = (TextView) v.findViewById(R.id.textView_time);
            title    = (TextView) v.findViewById(R.id.textView_title);
            body = (TextView) v.findViewById(R.id.textView_Body);
            color = (ImageView) v.findViewById(R.id.imageView_Color);


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
}
