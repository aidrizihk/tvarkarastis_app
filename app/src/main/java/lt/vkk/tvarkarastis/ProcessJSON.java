package lt.vkk.tvarkarastis;

import android.os.AsyncTask;

import com.activeandroid.ActiveAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    item.remoteId = row.getInt("id");
                    item.pavadinimas = row.getString("pavadinimas");
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
                    item.remoteId = row.getInt("id");
                    item.vardas = row.getString("vardas");
                    item.pavarde = row.getString("pavarde");
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
                    item.remoteId = Integer.parseInt(row.getString("id"));
                    item.savDiena = row.getInt("diena");
                    item.pradzia = row.getString("pradzia");
                    item.pabaiga = row.getString("pabaiga");

                    int grupe = row.getInt("grupe");
                    item.grupe = Grupe.getSelected(grupe);

                    item.dalykas = row.getString("dalykas");

                    int destytojas = row.getInt("destytojas");
                    item.destytojas = Destytojas.getSelected(destytojas);

                    item.auditorija = row.getString("auditorija");
                    item.pogrupis = row.getInt("pogrupis");
                    item.pasikatojamumas = row.getInt("pasikartojamumas");
                    item.pasirenkamasis = row.getInt("pasirenkamasis");
                    item.save();

                    System.out.println("row.getString(\"pradzia\"):: " + row.getString("pradzia"));
                    System.out.println("row.getString(\"pabaiga\"):: " + row.getString("pabaiga"));
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
        //return stream;
        return urlString;
    }

    protected void onPostExecute() {
    } // onPostExecute() end

} // ProcessJSON class end