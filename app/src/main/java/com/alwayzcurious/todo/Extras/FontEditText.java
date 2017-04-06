package com.alwayzcurious.todo.Extras;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by vivek on 4/6/2017.
 */

public class FontEditText extends EditText {

    public FontEditText(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getApplicationContext()
                .getAssets(), String.format("font/%s", "Bariol_Regular.otf"));
        this.setTypeface(face);
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getApplicationContext()
                .getAssets(), String.format("font/%s", "Bariol_Regular.otf"));
        this.setTypeface(face);
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
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
