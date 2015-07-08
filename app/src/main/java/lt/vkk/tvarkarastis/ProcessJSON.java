package lt.vkk.tvarkarastis;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lt.vkk.tvarkarastis.table.TblGrupe;

/**
 * Created by tvarkarastis on 2015.07.01.
 */
class ProcessJSON extends AsyncTask<String, Void, String> {


    // Hashmap for ListView
    //ArrayList<HashMap<String, String>> contactList;

    protected String doInBackground(String... strings){
        String stream = null;
        String urlString = strings[0];

        HTTPDataHandler hh = new HTTPDataHandler();
        stream = hh.GetHTTPData(urlString);

        // Return the data from specified url
        return stream;
    }
    private MainActivity activity;
    public ProcessJSON(MainActivity activity) {
        this.activity = activity;
    }

    protected void onPostExecute(String stream){
        //TextView tv = (TextView) findViewById(R.id.tv);
        //tv.setText(stream);

        //..........Process JSON DATA................
        if(stream != null){
            try{
                // Get the full HTTP Data as JSONObject
                //JSONArray reader = new JSONArray(stream);
                JSONObject jsonArray = new JSONObject(stream);
                parseGrupe(jsonArray.optJSONArray("grupe"));
                parseDestytojas(jsonArray.optJSONArray("destytojas"));

                DatabaseHelper db = new DatabaseHelper(activity);
                final ArrayList<String> listGrupe;
                TblGrupe tblGrupe = new TblGrupe(activity);
                listGrupe = tblGrupe.getPavadinimas(db);
                activity.setListGrupe(listGrupe);
            }catch(JSONException e){
                e.printStackTrace();
            }
        } // if statement end
    } // onPostExecute() end

    private void parseGrupe(JSONArray allGroups) throws JSONException {
        // Grupe
        DatabaseHelper db = new DatabaseHelper(activity);
        int len = allGroups.length();

        // Scan each.
        for (int i = 0; i < len; i++) {
            // Get single row.
            JSONObject all_grupe = allGroups.getJSONObject(i);

            // Insert values to SQLite.
            db.insertGrupeWithID(
                    all_grupe.getInt("id"),
                    all_grupe.getString("pavadinimas")
            );
        }
        db.close();
    }

    private void parseDestytojas(JSONArray allDestytojas) throws JSONException {
        DatabaseHelper db = new DatabaseHelper(activity);
        int len = allDestytojas.length();

        // Scan each.
        for (int i = 0; i < len; i++) {
            // Get single row.
            JSONObject all_destytojas = allDestytojas.getJSONObject(i);

            // Insert values to SQLite.
            db.insertDestytojasWithID(
                    all_destytojas.getInt("id"),
                    all_destytojas.getString("vardas"),
                    all_destytojas.getString("pavarde")
            );
        }
        db.close();
    }
} // ProcessJSON class end
