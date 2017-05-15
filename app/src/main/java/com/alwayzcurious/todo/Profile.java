package com.alwayzcurious.todo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import java.util.Calendar;
import java.util.Locale;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alwayzcurious.todo.Extras.Validator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends AppCompatActivity {


    TextView currentDOB; EditText uName;
    Calendar c;
    Context mContext;
    Button changeProfileImage,saveInfo;
    CircleImageView circleImageView;
    Uri profileImageUrl=null;
    Validator validator;

    ProgressDialog alertDialogue;
    final private static int GALLERY_IMAGE_INTENT = 646;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        uName = (EditText) findViewById(R.id.editTextView_name);

        try {
            uName.setText(getIntent().getStringExtra("name"));
        }catch (Exception e){ Log.e("TODO","Uname not defined");}
        alertDialogue = new ProgressDialog(Profile.this);
        alertDialogue.setIndeterminate(true);
        alertDialogue.setMessage("Please Wait While we Build your Profile");
        saveInfo = (Button) findViewById(R.id.button_save_profile);
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Toast.makeText(Profile.this,"min length 4 and no special char",Toast.LENGTH_LONG).show();

                if(!isValidDOB())
                {

                    return;

                }

                if(!isValidName(uName.getText().toString()) || (uName.getText().toString().length() <4))
                {
                    alertDialogue.setIndeterminate(false);
                    alertDialogue.setMessage("Hmm! No special char in name and More than 4 char");
                    alertDialogue.show();
                    return;
                }

                alertDialogue.setIndeterminate(true);
                alertDialogue.setMessage("Please Wait While we Build your Profile");
                alertDialogue.show();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().
                        child("users/"+StaticInformation.FIREBASE_UID+"/birthday");
                         databaseReference.setValue(currentDOB.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    alertDialogue.dismiss();
                                     finish();
                                }

                            }
                });
            }

        });

        mContext=this;
        changeProfileImage = (Button) findViewById(R.id.buttonChangeProfileImage);
        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent();
                t.setType("image/*");
                t.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(t,GALLERY_IMAGE_INTENT);
            }
        });


        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        ////TODO fix the error string nullpointer
       // circleImageView.setImageURI(Uri.parse(getIntent().getExtras().get("image").toString()));

        Glide.with(mContext).load(getIntent().getExtras().get("image").toString())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleImageView);
        Log.d("test",getIntent().getExtras().get("image").toString());

        currentDOB = (TextView) findViewById(R.id.textViewDOB);
        setDOB();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SpannableString s = new SpannableString("Profile");
        s.setSpan(new TypefaceSpan(this,"Bariol_Regular.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

       // Update the action bar title with the TypefaceSpan instance

        myToolbar.setTitle(s);
        myToolbar.setNavigationIcon(R.drawable.ic_options);
        myToolbar.setBackgroundColor(Color.WHITE);
        myToolbar.setTitleTextColor(Color.BLACK);

        currentDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        currentDOB.setText(String.format(Locale.ENGLISH,"%2d / %02d / %4d",day,
                                month+1,year));
                        c.set(year,month,day);
                    }
                },c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
    }

    private boolean isValidName(String s) {
        if(s.matches("^[a-zA-Z\\s]*$"))
            return true;
        else
            Log.d("Profile","Regex not match");
            return false;
    }

    private boolean isValidDOB() {
        Calendar today = Calendar.getInstance();

        if(c.get(Calendar.YEAR) <= today.get(Calendar.YEAR)) {
            if (c.get(Calendar.MONTH) <= today.get(Calendar.MONTH))
                if (c.get(Calendar.DAY_OF_MONTH) < today.get(Calendar.DAY_OF_MONTH)) {
                    Log.d("Profile", "true=>" + c.get(Calendar.YEAR) + "=>" + today.get(Calendar.YEAR));
                    Log.d("Profile", "true=>" + c.get(Calendar.MONTH) + "=>" + today.get(Calendar.MONTH));
                    Log.d("Profile", "true=>" + c.get(Calendar.DAY_OF_MONTH) + "<" + today.get(Calendar.DAY_OF_MONTH));

                    return true;
                }

        }

        alertDialogue.setMessage("Hmm! Not a Valid Birthday");
        alertDialogue.show();
        Log.d("Problem","false=>"+c.get(Calendar.YEAR) +"<="+ today.get(Calendar.YEAR)+" " +(c.get(Calendar.YEAR) <= today.get(Calendar.YEAR)));
        Log.d("Problem","false=>"+ c.get(Calendar.MONTH) +"<="+today.get(Calendar.MONTH)+" "+(c.get(Calendar.MONTH) <= today.get(Calendar.MONTH)));
        Log.d("Problem","false=>"+ c.get(Calendar.DAY_OF_MONTH) +"<"+today.get(Calendar.DAY_OF_MONTH)+" "+(c.get(Calendar.DAY_OF_MONTH) < today.get(Calendar.DAY_OF_MONTH)));
            return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data.getData() !=null)
        {
            switch (requestCode)
            {
                case GALLERY_IMAGE_INTENT : {

                    circleImageView.setImageURI(data.getData());
                    profileImageUrl = data.getData();

                } break;

                default:
                    Toast.makeText(mContext,"there was problem",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void setDOB() {

        c = Calendar.getInstance();
        currentDOB.setText(String.format(Locale.ENGLISH,"%2d / %02d / %4d",c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.MONTH)+1,
                c.get(Calendar.YEAR)));

    }
}
