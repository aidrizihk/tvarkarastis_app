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

                JSONArray allGroups = reader.getJSONArray("grupe");
                DatabaseHelper db = new DatabaseHelper(context);

                //db.addGrupeWithID(allGroups);
                // Scan each.
                for (int i = 0; i < allGroups.length(); i++) {
                    // Get single row.
                    JSONObject all_grupe = allGroups.getJSONObject(i);

                    // Insert values to SQLite.
                    db.addGrupeWithID(
                            all_grupe.getInt("id"),
                            all_grupe.getString("pavadinimas"),
                            all_grupe.getString("elpastas"),
                            all_grupe.getInt("fakultetas_id"),
                            all_grupe.getInt("studijos_id"),
                            all_grupe.getInt("forma_id"),
                            all_grupe.getInt("istojimas")
                    );
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

        } // if statement end
    } // onPostExecute() end
} // ProcessJSON class end
