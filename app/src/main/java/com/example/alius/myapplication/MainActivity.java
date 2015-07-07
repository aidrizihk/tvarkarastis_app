package com.example.alius.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.alius.myapplication.table.TblGrupe;

import java.util.ArrayList;

public class MainActivity extends Activity{
    private static String urlString;
    private DatabaseHelper db;
    //private TblGrupe tblGrupe;

    public static final String PREFS_NAME = "tvarkarastisPrefs";


    //private Button btnIamStudent = (Button) findViewById(R.id.iam_student);
    //private Button btnIamLecturer = (Button) findViewById(R.id.iam_lecturer);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Check if app is runned first time.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); // Get preferences file (0 = no option flags set)
        boolean firstRun = settings.getBoolean("firstRun", true); // Is it first run? If not specified, use "true"
        if (firstRun) {
            Log.w("aliusa", "### first time");
            setContentView(R.layout.activity_main);
            SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
            editor.putBoolean("firstRun", false); // It is no longer the first run
            editor.commit(); // Save all changed settings

            // Connects and creates tables.
            DatabaseHelper db = new DatabaseHelper(this);

            // Check if connected to wifi or mobile internet.
            if (AppStatus.getInstance(this).isOnline()) {
                Toast.makeText(this, "You are online!!!! :)", Toast.LENGTH_SHORT).show();
                Log.w("aliusa","### you're online!");

                /** Check if data needs updated. **/
                //
                //

                // Parse JSON
                urlString = ""; // JSON array of objects.
                if (urlString.length() > 1) {
                    new ProcessJSON(this).execute(urlString);
                    Log.w("aliusa", "### len>1");
                } else {
                    Log.w("aliusa", "### initial processjson was not run");
                }
            } else {
                Toast.makeText(this, "Reikalingas interneto ryšys!", Toast.LENGTH_SHORT).show();
                Log.v("aliusa", "### You are not online!!!!");

                // Check if prefs set to lecturer/student and which.
                Boolean whoIam = settings.getBoolean("whoIam", false); // Is it set?
                String whoIam2 = settings.getString("whoIam2", ""); // To whom it set.
                Integer whoIam3 = settings.getInt("whoIam3", -1); // To which it's set.
                if (whoIam && ((whoIam2 == "student") || (whoIam2 == "lecturer")) && (whoIam3 >=0)) {
                    // TO DO
                    // go to next activity
                }
            }

        } else {
            Log.w("aliusa", "second time");
            //setContentView(R.layout.activity_clean_weather);
        }



        final Button btnSubmit = (Button) findViewById(R.id.btn_confirm_whoiam_selection);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ArrayList<String> listGrupe;
        TblGrupe tblGrupe = new TblGrupe(this);
        listGrupe = tblGrupe.getPavadinimas();


        // Creates alert dialog.
        //final String[] option = {"Add","View","Change","Delete"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, listGrupe);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_your_group);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println("selected: " + which);
                // Display "Patvirtinti" button
                // Onclick save to prefs
                // Go to next activity.
                Toast.makeText(getApplicationContext(), which, Toast.LENGTH_SHORT).show();
                btnSubmit.setVisibility(View.VISIBLE);
            }
        });
        final AlertDialog a = builder.create();


        final Button btnIamLecturer = (Button) findViewById(R.id.iam_lecturer);
        btnIamLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "lecturer btn clicked", Toast.LENGTH_SHORT).show();
            }
        });

        final Button btnIamStudent = (Button) findViewById(R.id.iam_student);
        btnIamStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.show();
                //Toast.makeText(getApplicationContext(), "student btn clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
} // Activity end