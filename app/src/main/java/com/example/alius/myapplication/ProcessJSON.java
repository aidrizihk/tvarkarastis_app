package com.example.alius.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alius on 2015.07.01.
 */
class ProcessJSON extends AsyncTask<String, Void, String> {


    // Hashmap for ListView
    //ArrayList<HashMap<String, String>> contactList;
    private Context context;
    protected String doInBackground(String... strings){
        String stream = null;
        String urlString = strings[0];

        HTTPDataHandler hh = new HTTPDataHandler();
        stream = hh.GetHTTPData(urlString);

        // Return the data from specified url
        return stream;
    }

    public ProcessJSON(Context context) {
        this.context = context;
    }

    protected void onPostExecute(String stream){
        //TextView tv = (TextView) findViewById(R.id.tv);
        //tv.setText(stream);

        //..........Process JSON DATA................
        if(stream != null){
            try{
                // Get the full HTTP Data as JSONObject
                JSONObject reader = new JSONObject(stream);

                parseGrupe(reader.getJSONArray("grupe"));
                parseDestytojas(reader.getJSONArray("destytojas"));
            }catch(JSONException e){
                e.printStackTrace();
            }
        } // if statement end
    } // onPostExecute() end

    private void parseGrupe(JSONArray allGroups) throws JSONException {
        // Grupe
        DatabaseHelper db = new DatabaseHelper(context);
        int len = allGroups.length();

        // Scan each.
        for (int i = 0; i < len; i++) {
            // Get single row.
            JSONObject all_grupe = allGroups.getJSONObject(i);

            // Insert values to SQLite.
            db.addGrupeWithID(
                    all_grupe.getInt("id"),
                    all_grupe.getString("pavadinimas")
            );
        }
    }

    private void parseDestytojas(JSONArray allDestytojas) throws JSONException {
        DatabaseHelper db = new DatabaseHelper(context);
        int len = allDestytojas.length();

        // Scan each.
        for (int i = 0; i < len; i++) {
            // Get single row.
            JSONObject all_destytojas = allDestytojas.getJSONObject(i);

            // Insert values to SQLite.
            db.addDestytojasWithID(
                    all_destytojas.getInt("id"),
                    all_destytojas.getString("vardas"),
                    all_destytojas.getString("pavarde")
            );
        }
    }
} // ProcessJSON class end
