package com.example.alius.myapplication;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity{
    private static String urlString;

    public static final String PREFS_NAME = "tvarkarastisPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if app is runned first time.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); // Get preferences file (0 = no option flags set)
        boolean firstRun = settings.getBoolean("firstRun", true); // Is it first run? If not specified, use "true"
        if (firstRun) {
            Log.w("aliusa", "first time");
            setContentView(R.layout.activity_main);
            SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
            editor.putBoolean("firstRun", false); // It is no longer the first run
            editor.commit(); // Save all changed settings


            // Connects and creates tables.
            DatabaseHelper db = new DatabaseHelper(this);
            //db.getWritableDatabase();
            // Parses data from remote host JSON.
            new ProcessJSON();
            //db.close();
        } else {
            Log.w("aliusa", "second time");
            //setContentView(R.layout.activity_clean_weather);
        }


        // Check if connected to wifi or mobile internet.
        if (AppStatus.getInstance(this).isOnline()) {
            Toast.makeText(this, "You are online!!!! :)", Toast.LENGTH_SHORT).show();

            /** Check if data needs updated. **/
            //
            //

            // Parse JSON
            urlString = "";
            new ProcessJSON().execute(urlString);
        } else {
            Toast.makeText(this, "You are not online!!!! :(", Toast.LENGTH_SHORT).show();
            Log.v("Home", "############################You are not online!!!!");
        }


        final Button btnIamLecturer = (Button) findViewById(R.id.iam_lecturer);
        btnIamLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "lecturer btn clicked", Toast.LENGTH_SHORT).show();
            }
        });

        final Button btnIamStudent = (Button) findViewById(R.id.iam_student);
        btnIamStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "student btn clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
} // Activity end