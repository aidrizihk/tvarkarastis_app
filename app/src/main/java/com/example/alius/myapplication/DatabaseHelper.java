package com.example.alius.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // --------------------------------VARIABLES---------------------------------------------

    protected static final String DATABASE_NAME = "tvarkarastis";
    protected static final int DATABASE_VERSION = 1;

    /**
     * Table Grupe.
     **/
    protected static final String grupeTable = "grupe";
    protected static final String colGrupeID = "grupeID";
    protected static final String colGrupePavadinimas = "pavadinimas";
    protected static final String colElpastas = "elpastas";
    protected static final String colFakultetasID = "fakultetas";
    protected static final String colStudijosID = "studijos";
    protected static final String colFormaID = "forma";
    protected static final String colistojimas = "istojimas";
    private static final String CREATE_TABLE_GRUPE =
            "CREATE TABLE " + grupeTable + "("
                    + colGrupeID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
                    + colGrupePavadinimas + " VARCHAR NOT NULL  DEFAULT (NULL),"
                    + colElpastas + " VARCHAR,"
                    + colFakultetasID + " INTEGER,"
                    + colStudijosID + " INTEGER,"
                    + colFormaID + " INTEGER,"
                    + colistojimas + " INTEGER"
                    + ")";
    String INDEX = "CREATE UNIQUE INDEX locations_index ON "
            + grupeTable + " ("+colGrupePavadinimas+")";

    /**
     * Table Destytojas.
     **/
    protected static final String destytojasTable = "destytojas";
    protected static final String colDestytojasID = "destytojasID";
    protected static final String colDestytojasVardas = "vardas";
    protected static final String colDestytojasPavarde = "pavarde";
    private static String CREATE_TABLE_DESTYTOJAS =
            "CREATE TABLE " + destytojasTable + "("
                    + colDestytojasID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
                    + colDestytojasVardas + " VARCHAR NOT NULL, "
                    + colDestytojasPavarde + " VARCHAR NOT NULL "
                    + ")";

    /**
     * Table Dalykas.
     **/
    protected static final String dalykasTable = "dalykas";
    protected static final String colDalykasID = "dalykasID";
    protected static final String colDalykasPavadinimas = "pavadinimas";
    private static String CREATE_TABLE_DALYKAS =
            "CREATE TABLE " + dalykasTable + "("
                    + colDalykasID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
                    + colDalykasPavadinimas + " VARCHAR NOT NULL "
                    + ")";

    // -----------------------------MAIN-METHODS----------------------------------------------

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // Create tables with schemas.
        db.execSQL(CREATE_TABLE_GRUPE);
        db.execSQL(INDEX);
        db.execSQL(CREATE_TABLE_DESTYTOJAS);
        db.execSQL(CREATE_TABLE_DALYKAS);

        Log.w("aliusa", "Databases created");
    }

    /*public void isertRecord(HashMap<String, String> queryValues) {
        ContentValues values = new ContentValues();
    }*/

    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DATABASE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void addGrupeWithID(int id, String pavadinimas, String elpastas, int fakultetas, int studijos, int forma, int istojimas) {
        // you can use INSERT only
        String sql = "INSERT OR REPLACE INTO " + grupeTable + " VALUES(?,?,?,?,?,?,?)";
        SQLiteDatabase db = this.getWritableDatabase();

        /*
         * According to the docs http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
         * Writers should use beginTransactionNonExclusive() or beginTransactionWithListenerNonExclusive(SQLiteTransactionListener)
         * to start a transaction. Non-exclusive mode allows database file to be in readable by other threads executing queries.
         */
        if (Build.VERSION.SDK_INT > 11) {
            db.beginTransactionNonExclusive();
        } else {
            db.beginTransaction();
        }

        SQLiteStatement stmt = db.compileStatement(sql);

        //for(int x = 1; x <= arr.; x++){

            //stmt.bindString(arr);
            stmt.bindLong(1, id);
            stmt.bindString(2, pavadinimas);
            stmt.bindString(3, elpastas);
            stmt.bindLong(4, fakultetas);
            stmt.bindLong(5, studijos);
            stmt.bindLong(6, forma);
            stmt.bindLong(7, istojimas);

            stmt.execute();
            stmt.clearBindings();

        //}

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        Log.w("aliusa", "įdėta nauja grupė į sqlite");
    }
}
