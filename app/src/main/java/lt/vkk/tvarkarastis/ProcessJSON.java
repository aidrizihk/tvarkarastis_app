package lt.vkk.tvarkarastis;

import android.os.AsyncTask;

import com.activeandroid.ActiveAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lt.vkk.tvarkarastis.models.Destytojas;
import lt.vkk.tvarkarastis.models.Grupe;
import lt.vkk.tvarkarastis.models.PaskaitosIrasas;

/**
 * Created by alius on 2015.07.21.
 */
public class ProcessJSON extends AsyncTask<String, Void, String> {

    private MainActivity activity;

    public ProcessJSON(MainActivity activity) {
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... strings) {
        String urlString = strings[0];

        HTTPDataHandler hh = new HTTPDataHandler();
        String stream = hh.GetHTTPData(urlString);
        //System.out.println("stream:: " + stream);
        //..........Process JSON DATA................
        if (stream != null) try {
            // Get the full HTTP Data as JSONObject
            //JSONArray reader = new JSONArray(stream);
            JSONObject jsonArray = new JSONObject(stream);

            ActiveAndroid.beginTransaction();
            try {
                // Grupė.
                JSONArray array = jsonArray.optJSONArray("grupe");
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    JSONObject row = array.getJSONObject(i);
                    Grupe item = new Grupe();
                    item.setRemoteId(row.getInt("id"));
                    item.setPavadinimas(row.getString("pavadinimas"));
                    item.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

            ActiveAndroid.beginTransaction();
            try {
                // Dėstytojas
                JSONArray array = jsonArray.optJSONArray("destytojas");
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    JSONObject row = array.getJSONObject(i);
                    Destytojas item = new Destytojas();
                    item.setRemoteId(row.getInt("id"));
                    item.setVardas(row.getString("vardas"));
                    item.setPavarde(row.getString("pavarde"));
                    item.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

            ActiveAndroid.beginTransaction();
            try {
                // Savatės paskaita
                JSONArray array = jsonArray.optJSONArray("savaites_paskaita");
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    JSONObject row = array.getJSONObject(i);
                    PaskaitosIrasas item = new PaskaitosIrasas();
                    item.setRemoteId(row.getInt("id"));
                    item.setSavDiena(row.getInt("diena"));
                    item.setPradzia(row.getString("pradzia"));
                    item.setPabaiga(row.getString("pabaiga"));

                    int grupe = row.getInt("grupe");
                    item.setGrupe_int(grupe);
                    item.setGrupe(Grupe.getSelected(grupe));

                    item.setDalykas(row.getString("dalykas"));

                    int destytojas = row.getInt("destytojas");
                    item.setDestytojas_int(destytojas);
                    item.setDestytojas(Destytojas.getSelected(destytojas));

                    item.setAuditorija(row.getString("auditorija"));
                    item.setPogrupis(row.getInt("pogrupis"));
                    item.setPasikatojamumas(row.getInt("pasikartojamumas"));
                    item.setPasirenkamasis(row.getInt("pasirenkamasis"));
                    item.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return the data from specified url
        //return stream;
        return urlString;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        activity.setDestytojas((ArrayList<Destytojas>) Destytojas.getAllList());
        activity.setGrupe((ArrayList<Grupe>) Grupe.getAllList());
        activity.setPaskaitos((ArrayList<PaskaitosIrasas>) PaskaitosIrasas.getAllList());
        activity.btnIamLecturer.setEnabled(true);
        activity.btnIamStudent.setEnabled(true);
    }

} // ProcessJSON class end
