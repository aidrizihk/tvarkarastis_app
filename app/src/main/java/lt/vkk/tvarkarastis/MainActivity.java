package lt.vkk.tvarkarastis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Delete;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lt.vkk.tvarkarastis.models.Destytojas;
import lt.vkk.tvarkarastis.models.Grupe;
import lt.vkk.tvarkarastis.models.PaskaitosIrasas;

public class MainActivity extends AppCompatActivity {

    protected static final String PREFS_NAME = "tvarkarastisPrefs";
    Context mContext;
    SharedPreferences mSettings;
    ArrayList<Grupe> grupe;
    ArrayList<Destytojas> destytojas;
    ArrayList<PaskaitosIrasas> paskaitos;
    @Bind(R.id.btn_confirm_whoiam)
    Button btnSubmit;
    @Bind(R.id.btn_iam_lecturer)
    Button btnIamLecturer;
    @Bind(R.id.btn_iam_student)
    Button btnIamStudent;
    private AlertDialog.Builder dialogBuilder;

    public void setGrupe(ArrayList<Grupe> grupe) {
        this.grupe = grupe;
    }

    public void setDestytojas(ArrayList<Destytojas> destytojas) {
        this.destytojas = destytojas;
    }

    public void setPaskaitos(ArrayList<PaskaitosIrasas> paskaitos) {
        this.paskaitos = paskaitos;
    }

    // Check internet connection.
    public boolean isDeviceOnline() {
        // Check if connected to wifi or mobile internet.
        if (AppStatus.getInstance(mContext).isOnline()) {
            return true;
        } else {
            Toast.makeText(mContext, "Reikalingas interneto ryšys!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Parses JSON data from given URL.
    public void parseData() {
        String urlString = App.URL + App.PATH;
        Log.v("ali", "url:: " + urlString);
        new ProcessJSON(this).execute(urlString);
        // TODO: add http checker response.
        //Toast.makeText(this, "Serveris išjungtas. Duomenų gauti neįmanoma!", Toast.LENGTH_SHORT).show();
    }

    // Write to SharedPrefs who's, lecturer/student and which one of those selected.
    private void setEditor(int who, int which) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt("whoIam2", who); // 1 - lecturer, 2 - student, 0 - none
        editor.putInt("whoIam3", which); // Which of those is it.
        editor.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Injects ButterKnife lib.
        ButterKnife.bind(this);


        // ActiveAndroid config.
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        // Select classes for DB.
        configurationBuilder.addModelClasses(Destytojas.class, Grupe.class, PaskaitosIrasas.class);
        // Creates SQLite database using ActiveAndroid library for given classes.
        ActiveAndroid.initialize(this);

        // Get preferences file (0 = no option flags set)
        mSettings = getSharedPreferences(PREFS_NAME, 0);

        // Check if app is runned first time, if not use "true"
        boolean firstRun = mSettings.getBoolean("firstRun", true);
        if (firstRun) {

            mContext = getApplicationContext();

            SharedPreferences.Editor editor = mSettings.edit(); // Open the editor for our settings
            editor.putBoolean("firstRun", false); // It is no longer the first run
            editor.commit(); // Save all changed settings

            // If connected to internet parse JSON to ActiveAndroid.
            if (isDeviceOnline()) {
                /** Check if data needs updated. **/
                //
                //


                parseData();
            }
        } else {
            // Check if prefs set to lecturer/student and which.
            Boolean whoIam = mSettings.getBoolean("whoIam", false); // Is it set?
            int whoIam2 = mSettings.getInt("whoIam2", 0); // To which it's set.
            int whoIam3 = mSettings.getInt("whoIam3", 0); // To which it's set.
            if (whoIam && (whoIam2 != 0) && (whoIam3 != 0)) {
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
                SharedPreferences.Editor editor = mSettings.edit(); // Open the editor for our settings
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
                final DestytojasAdapter adapter =
                        new DestytojasAdapter(MainActivity.this, R.layout.listview_item_row, destytojas);

                // Build Alert Dialog.
                dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Pasirink save!");
                dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get selected Group name.
                        String selected =
                                destytojas.get(which).getPavarde() + ", " + destytojas.get(which).getVardas();
                        // Return to user selected Group.
                        Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();

                        // Get Selected remoteId, Cast selected ID to int.
                        int remoteId = (int) (long) destytojas.get(which).getRemoteId();

                        // Save Selected Id.
                        setEditor(1, remoteId); // 1 - lecturer

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
                        setEditor(2, remoteId); // 2 - student

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
                if (isDeviceOnline())
                    parseData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*
 *
 *
 *
 *
 */
    public class DestytojasAdapter extends ArrayAdapter<Destytojas> {

        Context context;
        int layoutResourceId;
        List<Destytojas> data = null;

        public DestytojasAdapter(Context context, int resource, List<Destytojas> objects) {
            super(context, resource, objects);
            this.layoutResourceId = resource;
            this.context = context;
            this.data = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            DestytojasHolder holder;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new DestytojasHolder();
                holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

                row.setTag(holder);
            } else {
                holder = (DestytojasHolder) row.getTag();
            }

            Destytojas destytojas = data.get(position);
            holder.txtTitle.setText(destytojas.getPavarde() + ", " + destytojas.getVardas());

            return row;
        }

        class DestytojasHolder {
            TextView txtTitle;
        }
    }

    /*
     *
     *
     *
     *
     */
    public class GrupeAdapter extends ArrayAdapter<Grupe> {

        Context context;
        int layoutResourceId;
        List<Grupe> data = null;

        public GrupeAdapter(Context context, int resource, List<Grupe> objects) {
            super(context, resource, objects);
            this.layoutResourceId = resource;
            this.context = context;
            this.data = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            GrupeHolder holder;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new GrupeHolder();
                holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

                row.setTag(holder);
            } else {
                holder = (GrupeHolder) row.getTag();
            }

            Grupe grupe = data.get(position);
            holder.txtTitle.setText(grupe.getPavadinimas());

            return row;
        }

        class GrupeHolder {
            TextView txtTitle;
        }
    }

}
