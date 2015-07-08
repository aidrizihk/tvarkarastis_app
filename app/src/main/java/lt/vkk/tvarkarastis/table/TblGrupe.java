package lt.vkk.tvarkarastis.table;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import lt.vkk.tvarkarastis.DatabaseHelper;

public class TblGrupe extends DatabaseHelper {
    //private DatabaseHelper db;

    public Integer id;
    public String pavadinimas;

    private static TblGrupe instance;

    public static synchronized TblGrupe getHelper(Context context) {
        if (instance == null)
            instance = new TblGrupe(context);
        return instance;
    }

    public static Context mContext;

    public TblGrupe(Context context) {
        super(context);
        this.mContext = context;
    }

    public ArrayList<String> getPavadinimas(DatabaseHelper db) {
        ArrayList<String> listGrupe = new ArrayList<>();
        //DatabaseHelper db = new DatabaseHelper(mContext);
        String selectQuery = String.format("SELECT " + db.getColGrupePavadinimas() + " FROM " + db.getGrupeTable());
        Cursor cursor;
        cursor = db.getWritableDatabase().rawQuery(selectQuery, null);
        //Log.w("aliusa", "### data: "+ data);
        //System.out.println("cursor.getColumnName: " + cursor.getColumnName(1));
        //System.out.println("cursor.getColumnName: " + cursor.getColumnName(1));
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int num = cursor.getColumnIndex(getColGrupePavadinimas());
                do {
                    String data = cursor.getString(num);
                    //System.out.println("data: " + data);
                    listGrupe.add(data);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.w("aliusa", e);
            //System.out.println(e);
        } finally {
            db.close();
            cursor.close();
        }
        return listGrupe;
    }
    // Get data of all groups.
    /*public List<TblGrupe> getAllGrupe() {
        //DatabaseHelper db = new DatabaseHelper(Context);
       *//*if (instance = null) {
        }
        DatabaseHelper instance = new DatabaseHelper();*//*
        //DatabaseHelper db = new DatabaseHelper();
        List<TblGrupe> grupeList = new ArrayList<TblGrupe>();
        String selectQuery = String.format("SELECT * FROM %s", db.getGrupeTable());
        Cursor cursor = db.getReadableDatabase().rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TblGrupe data = new TblGrupe();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPavadinimas(cursor.getString(1));

                // Adding contact to list
                grupeList.add(data);
            } while (cursor.moveToNext());
        }
        return grupeList;
    }*/
}
