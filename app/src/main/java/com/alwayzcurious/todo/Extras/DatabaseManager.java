package com.alwayzcurious.todo.Extras;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vivek on 4/10/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    public static String DATABASE = "remindMe",TABLE ="events",
            title="title",description="description",identity="identity";
    public static String timeHr ="timeHr" , timeMin= "timeMin", timeSec ="timeSec", dateMonth="dateMonth" ,
            dateYear="dateYear" ,dateDay="dateDay",preTaskRepetitionMinute="preTaskRepetitionMinute",preTaskRepetition="preTaskRepetition",location="location";

    Cursor cursor;


    public DatabaseManager(Context context) {
        super(context,DATABASE,null,1 /*DB Version */);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists events (id integer primary key autoincrement, title text," +
                " description text, timeHr integer, timeMin integer, timeSec integer, dateMonth integer, " +
                "dateYear integer,dateDay integer, identity text,isFinished boolean, interval integer, isPreTaskRepetition boolean, isPostTaskRepetition boolean," +
                "preTaskRepetitionMinute integer, preTaskRepetition integer,location text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS events" );
        onCreate(sqLiteDatabase);
    }


    public String createTask(Task task)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(title,task.getTitle());
        values.put(description,task.description);
        values.put(identity,task.identity);
        values.put(timeHr,task.timeHr);
        values.put(timeMin,task.timeMin);
        values.put(timeSec,task.timeSec);
        values.put(dateDay,task.dateDay);
        values.put(dateMonth,task.dateMonth);
        values.put(dateYear,task.dateYear);
        values.put(preTaskRepetitionMinute,task.getPreTaskRepetitionMinutes());
        values.put(preTaskRepetition,task.preTaskFrequency);
        values.put(location,task.getLocation());

        long id= database.insert(TABLE,null,values);

        Log.d("TODO","insert id was "+id);

        Log.d("TODO","row inserted =>"+task.getDescription());

        return String.valueOf(id);

    }

    public Task getTaskById (String id)
    {

        String query="select * from events where id= "+ id;


        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Task task=null;
        if(cursor.moveToFirst())
        {
            do{

                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    Log.i("Database",cursor.getColumnName(i) +" =>"+i);
                }
                task = new Task();

                task.id=cursor.getInt(0);
                task.title = cursor.getString(1);
                task.description = cursor.getString(2);
                task.timeHr = Integer.parseInt(cursor.getString(3));
                task.timeMin = Integer.parseInt(cursor.getString(4));
                task.timeSec = Integer.parseInt(cursor.getString(5));
                task.dateMonth = Integer.parseInt(cursor.getString(6));
                task.dateYear = Integer.parseInt(cursor.getString(7));
                task.dateDay = Integer.parseInt(cursor.getString(8));
                task.identity = cursor.getString(9);
                task.preTaskFrequency = Integer.parseInt(cursor.getString(15));
                task.preTaskRepetitionMinutes = Integer.parseInt(cursor.getString(14));



            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return task;
    }

    public List<Task> getTodayTasks (int dateDay,int dateMonth,int dateYear, int hr, int minute,int sec)
    {

        String query="SELECT * FROM " + TABLE + "where dateYear >= "+dateYear +" and dateMonth >=" + dateMonth +" and (dateDay between "+ dateDay +
                        " and " + (dateDay+1) +" ) and timeHr >= "+hr +" and timeMin >= "+ minute +" and timeSec >= "+sec;

        List<Task> taskList = new ArrayList<>();

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do{
                Task task = new Task();

                task.id=cursor.getInt(0);
                task.title = cursor.getString(1);
                task.description = cursor.getString(2);
                task.timeHr = Integer.parseInt(cursor.getString(3));
                task.timeMin = Integer.parseInt(cursor.getString(4));
                task.timeSec = Integer.parseInt(cursor.getString(5));
                task.dateMonth = Integer.parseInt(cursor.getString(6));
                task.dateYear = Integer.parseInt(cursor.getString(7));
                task.dateDay = Integer.parseInt(cursor.getString(8));
                task.timeHr = Integer.parseInt(cursor.getString(9));
                task.identity = cursor.getString(9);
                task.preTaskFrequency = Integer.parseInt(cursor.getString(15));
                task.preTaskRepetitionMinutes = Integer.parseInt(cursor.getString(14));


                taskList.add(task);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return taskList;
    }

    public List<Task> getThisDayTask (int dateDay,int dateMonth,int dateYear, int hr, int minute,int sec)
    {

        String query="SELECT * FROM " + TABLE + " where dateYear >= "+dateYear +" and dateMonth >=" + dateMonth +" and dateDay = "+ dateDay
              /* +" and timeHr >= "+hr +" and timeMin >= "+ minute +" and timeSec >= "+sec*/;

        List<Task> taskList = new ArrayList<>();

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        Log.d("TODO","getThisDayTask "+query+" "+cursor.getCount());

        if(cursor.moveToFirst())
        {
            do{
                Task task = new Task();

                for(int i=0;i<cursor.getColumnCount();i++)
                Log.i("TODO",cursor.getColumnName(i)+"=>"+cursor.getString(i)+" "+i);

                task.id=cursor.getInt(0);
                task.title = cursor.getString(1);
                task.description = cursor.getString(2);
                task.timeHr = Integer.parseInt(cursor.getString(3));
                task.timeMin = Integer.parseInt(cursor.getString(4));
                task.timeSec = Integer.parseInt(cursor.getString(5));
                task.dateMonth = Integer.parseInt(cursor.getString(6));
                task.dateYear = Integer.parseInt(cursor.getString(7));
                task.dateDay = Integer.parseInt(cursor.getString(8));
                task.identity = cursor.getString(9);
                task.preTaskFrequency = Integer.parseInt(cursor.getString(15));
                task.preTaskRepetitionMinutes = Integer.parseInt(cursor.getString(14));

               // task.preTaskRepetition = Integer.parseInt(cursor.getString(12));


                taskList.add(task);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return taskList;
    }


    public String getThisDayTaskCount ()
    {
        int dateDay, dateMonth, dateYear,  hr,  minute, sec;
        Calendar calendar = Calendar.getInstance();

        dateMonth= calendar.get(Calendar.MONTH);
        dateYear= calendar.get(Calendar.YEAR);
        hr= calendar.get(Calendar.HOUR);
        minute= calendar.get(Calendar.MINUTE);
        sec= calendar.get(Calendar.SECOND);
        dateDay = calendar.get(Calendar.DAY_OF_MONTH);



        String query="SELECT * FROM " + TABLE + " where dateYear = "+dateYear +" and dateMonth =" + dateMonth +" and dateDay = "+ dateDay
               /* +" and timeHr >= "+hr +" and timeMin >= "+ minute +" and timeSec >= "+sec*/;


        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        String count = String.valueOf(cursor.getCount());

        cursor.close();
        return count;
    }


    public String getCompletedTask() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from events where isFinished =1",null);
        Log.d("TODO","DB compleed"+ cursor.getCount());
        return String.valueOf(cursor.getCount());
    }

    public String getPendingTask() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from events where isFinished =0 or isFinished is null ",null);
        Log.d("TODO","total pending task were"+ cursor.getCount());
        return String.valueOf(cursor.getCount());
    }

    public int getTotalTaskCount() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from events",null);
        return cursor.getCount();
    }

    public  void deleteTask(String id)
    {
        SQLiteDatabase db = getReadableDatabase();
        cursor = db.rawQuery("delete from events where id="+id,null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void markDone(String id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Log.d("TODO","markdone "+id);
        cursor = db.rawQuery("update events set isFinished = 1 where id = "+id,null);
        cursor.moveToFirst();
        cursor.close();
    }
}

