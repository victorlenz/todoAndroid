package com.alwayzcurious.todo.Extras;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by vivek on 4/1/2017.
 */

public class FontButton1 extends Button {


    public FontButton1(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getApplicationContext()
                .getAssets(), String.format("font/%s", "Bariol_Regular.otf"));
        this.setTypeface(face);
    }

    public FontButton1(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getApplicationContext()
                .getAssets(), String.format("font/%s", "Bariol_Regular.otf"));
        this.setTypeface(face);
    }

    public FontButton1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getApplicationContext()
                .getAssets(), String.format("font/%s", "Bariol_Regular.otf"));
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
}
