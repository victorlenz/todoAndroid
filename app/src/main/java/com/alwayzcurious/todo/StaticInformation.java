package com.alwayzcurious.todo;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;

/**
 * Created by vivek on 3/31/2017.
 */

public  class StaticInformation {

    public static String FIREBASE_UID,USERNAME,PROFILE_ICON_URL;

    void checkPermission(Context context)
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                ) {
            String[] ps = new String[1];
            ps[1] = "ACCESS_FINE_LOCATION";
            ActivityCompat.requestPermissions((Activity)context.getApplicationContext(),ps,89);

            return;
        }
    }



}
