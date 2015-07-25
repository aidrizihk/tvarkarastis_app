package lt.vkk.tvarkarastis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import java.util.ArrayList;

import lt.vkk.tvarkarastis.adapters.DestytojasAdapter;
import lt.vkk.tvarkarastis.adapters.GrupeAdapter;
import lt.vkk.tvarkarastis.models.Destytojas;
import lt.vkk.tvarkarastis.models.Grupe;
import lt.vkk.tvarkarastis.models.PaskaitosIrasas;

public class MainActivity extends AppCompatActivity {

    protected static final String PREFS_NAME = "tvarkarastisPrefs";
    // Temp for displaying
    ListView listView;
    //Button btnSubmit;
    Context mContext;
    private AlertDialog.Builder dialogBuilder;
    SharedPreferences settings;

    public void parseData() {
        String urlString = ""; // JSON array of objects.
        if (urlString.length() > 1) {
            new ProcessJSON(this).execute(urlString);
        } else {
            // TODO
        }
    }


    private void setEditor(String who, int which) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("whoIam2", who);
        editor.putInt("whoIam3", which);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ActiveAndroid config.
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        // Select classes for DB.
        configurationBuilder.addModelClasses(Destytojas.class, Grupe.class, PaskaitosIrasas.class);
        ActiveAndroid.initialize(this);

        settings = getSharedPreferences(PREFS_NAME, 0); // Get preferences file (0 = no option flags set)

        final Button btnSubmit = (Button) findViewById(R.id.btn_confirm_whoiam);
        btnSubmit.setEnabled(false);


        // Check if app is runned first time.
        boolean firstRun = settings.getBoolean("firstRun", true); // Is it first run? If not specified, use "true"
        if (firstRun) {
            mContext = getApplicationContext();

            Log.w("aliusa", "### first time");
            SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
            editor.putBoolean("firstRun", false); // It is no longer the first run
            editor.commit(); // Save all changed settings


            // Check if connected to wifi or mobile internet.
            if (AppStatus.getInstance(this).isOnline()) {
                //Toast.makeText(this, "You are online!!!! :)", Toast.LENGTH_SHORT).show();
                //Log.w("aliusa", "### you're online!");

                /** Check if data needs updated. **/
                //
                //

                // Parse JSON to ActiveAndroid.
                parseData();


            } else {
                Toast.makeText(this, "Reikalingas interneto ryšys!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Check if prefs set to lecturer/student and which.
            Boolean whoIam = settings.getBoolean("whoIam", false); // Is it set?
            /*String whoIam2 = settings.getString("whoIam2", ""); // To whom it set. student/lecturer
            Integer whoIam3 = settings.getInt("whoIam3", 0); // To which it's set. ID of it.*/
            if (whoIam/* && ((whoIam2 == "student") || (whoIam2 == "lecturer")) && (whoIam3 != 0)*/) {
                // Declare Tvarkaraštis activity.
                Intent intent = new Intent(getApplicationContext(), TvarkarastisActivity.class);
                // Starts Tvarkaraštis activity.
                startActivity(intent);
            }
        }

        //final Button btnSubmit = (Button) findViewById(R.id.btn_confirm_whoiam);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Onclick save to prefs
                SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
                editor.putBoolean("whoIam", true); // It is no longer the first run
                editor.commit(); // Save all changed settings

                Intent intent = new Intent(getApplicationContext(), TvarkarastisActivity.class);
                // Starts Tvarkaraštis activity.
                startActivity(intent);
            }
        });

        Button btnIamLecturer = (Button) findViewById(R.id.iam_lecturer);
        btnIamLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Query ActiveAndroid for list of data
                final ArrayList<Destytojas> items = (ArrayList<Destytojas>) Destytojas.getAllList();

                // Construct adapter plugging in the array source
                DestytojasAdapter adapter = new DestytojasAdapter(MainActivity.this, R.layout.listview_item_row, items);

                // Build Alert Dialog.
                dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Pasirink save!");
                dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get Selected remoteId, Cast selected ID to int.
                        int remoteIds = (int) (long) items.get(which).getId();

                        // Get selected Group name.
                        String selectedGrupe = items.get(which).pavarde + ", " + items.get(which).vardas;
                        // Return to user selected Group.
                        Toast.makeText(MainActivity.this, selectedGrupe + " - " + remoteIds, Toast.LENGTH_SHORT).show();

                        // Save Selected Id.
                        setEditor("lecturer", remoteIds);

                        // Enable Submit Button.
                        btnSubmit.setEnabled(true);
                    }
                });
                AlertDialog alertdialog = dialogBuilder.create();
                alertdialog.show();
            }
        });

        Button btnIamStudent = (Button) findViewById(R.id.iam_student);
        btnIamStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Query ActiveAndroid for list of data and cast to ArrayList.
                final ArrayList<Grupe> items = (ArrayList<Grupe>) Grupe.getAllList();

                // Construct adapter plugging in the array source
                GrupeAdapter adapter = new GrupeAdapter(MainActivity.this, R.layout.listview_item_row, items);

                // Build AlertDialog.
                dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Pasirink savo grupę!");
                dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get Selected remoteId, Cast selected ID to int.
                        int remoteIds = (int) (long) items.get(which).getId();

                        // Get selected Group name.
                        String selectedGrupe = items.get(which).pavadinimas;
                        // Return to user selected Group.
                        Toast.makeText(MainActivity.this, selectedGrupe, Toast.LENGTH_SHORT).show();

                        // Save Selected Id.
                        setEditor("student", remoteIds);

                        // Enable Submit Button.
                        btnSubmit.setEnabled(true);
                    }
                });
                AlertDialog alertdialog = dialogBuilder.create();
                alertdialog.show();
            }
        });
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
