package lt.vkk.tvarkarastis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Delete;

import java.util.ArrayList;

import lt.vkk.tvarkarastis.adapters.DestytojasAdapter;
import lt.vkk.tvarkarastis.adapters.GrupeAdapter;
import lt.vkk.tvarkarastis.models.Destytojas;
import lt.vkk.tvarkarastis.models.Grupe;
import lt.vkk.tvarkarastis.models.PaskaitosIrasas;

public class MainActivity extends AppCompatActivity {

    protected static final String PREFS_NAME = "tvarkarastisPrefs";
    Context mContext;
    SharedPreferences settings;
    ArrayList<Grupe> grupe;
    ArrayList<Destytojas> destytojas;
    ArrayList<PaskaitosIrasas> paskaitos;
    private AlertDialog.Builder dialogBuilder;
    Button btnSubmit;
    Button btnIamLecturer;
    Button btnIamStudent;

    public void setGrupe(ArrayList<Grupe> grupe) {
        this.grupe = grupe;
    }

    public void setDestytojas(ArrayList<Destytojas> destytojas) {
        this.destytojas = destytojas;
    }

    public void setPaskaitos(ArrayList<PaskaitosIrasas> paskaitos) {
        this.paskaitos = paskaitos;
    }

    // Creates SQLite database using ActiveAndroid library for given classes.
    public void CreateDatabase() {
        // ActiveAndroid config.
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        // Select classes for DB.
        configurationBuilder.addModelClasses(Destytojas.class, Grupe.class, PaskaitosIrasas.class);
        ActiveAndroid.initialize(this);
    }

    // Check internet connection.
    public void checkInternet() {
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
    }

    // Parses JSON data from given URL.
    public void parseData() {
		String urlString = ""; // JSON array of objects.
        if (urlString.length() > 1) {
            new ProcessJSON(this).execute(urlString);
        } else {
            // TODO
        }
    }

    // Write to SharedPrefs who's, lecturer/student and which one of those selected.
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
        CreateDatabase();

        // Get preferences file (0 = no option flags set)
        settings = getSharedPreferences(PREFS_NAME, 0);

        btnSubmit = (Button) findViewById(R.id.btn_confirm_whoiam);
        btnIamLecturer = (Button) findViewById(R.id.iam_lecturer);
        btnIamStudent = (Button) findViewById(R.id.iam_student);

        // Disables buttons while parsing data.
        btnSubmit.setEnabled(false);
        btnIamLecturer.setEnabled(false);
        btnIamStudent.setEnabled(false);

        // Check if app is runned first time.
        boolean firstRun = settings.getBoolean("firstRun", true); // Is it first run? If not specified, use "true"
        if (firstRun) {

            mContext = getApplicationContext();

            Log.w("aliusa", "### first time");
            SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
            editor.putBoolean("firstRun", false); // It is no longer the first run
            editor.commit(); // Save all changed settings

            checkInternet();

        } else {
            // Check if prefs set to lecturer/student and which.
            Boolean whoIam = settings.getBoolean("whoIam", false); // Is it set?
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
                // Onclick save who/which to prefs
                SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
                editor.putBoolean("whoIam", true); // It is no longer the first run
                editor.commit(); // Save all changed settings

                Intent intent = new Intent(getApplicationContext(), TvarkarastisActivity.class);
                // Starts Tvarkaraštis activity.
                startActivity(intent);
            }
        });


        btnIamLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Construct adapter plugging in the array source
                final DestytojasAdapter adapter = new DestytojasAdapter(MainActivity.this, R.layout.listview_item_row, destytojas);

                // Build Alert Dialog.
                dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Pasirink save!");
                dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get selected Group name.
                        String selected = destytojas.get(which).getPavarde() + ", " + destytojas.get(which).getVardas();
                        // Return to user selected Group.
                        Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();

                        // Get Selected remoteId, Cast selected ID to int.
                        int remoteId = (int) (long) destytojas.get(which).getRemoteId();

                        // Save Selected Id.
                        setEditor("lecturer", remoteId);

                        // Enable Submit Button.
                        btnSubmit.setEnabled(true);
                    }
                });
                AlertDialog alertdialog = dialogBuilder.create();
                alertdialog.show();
            }
        });


        btnIamStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Construct adapter plugging in the array source
                final GrupeAdapter adapter = new GrupeAdapter(MainActivity.this, R.layout.listview_item_row, grupe);

                // Build AlertDialog.
                dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Pasirink savo grupę!");
                dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get selected Group name.
                        String selected = grupe.get(which).getPavadinimas();
                        // Return to user selected Group.
                        Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();

                        // Get Selected remoteId, Cast selected ID to int.
                        int remoteId = (int) (long) grupe.get(which).getRemoteId();

                        // Save Selected Id.
                        setEditor("student", remoteId);

                        // Enable Submit Button.
                        btnSubmit.setEnabled(true);
                    }
                });
                AlertDialog alertdialog = dialogBuilder.create();
                alertdialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            // Refresh button
            case R.id.action_refresh:
                // Disables buttons while deleting, parsing data.
                btnIamLecturer.setEnabled(false);
                btnIamStudent.setEnabled(false);
                btnSubmit.setEnabled(false);

                // Deletes data from SQLite.
                new Delete().from(PaskaitosIrasas.class).execute();
                new Delete().from(Destytojas.class).execute();
                new Delete().from(Grupe.class).execute();
                // Check internet, parses JSON, sets objects.
                checkInternet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
