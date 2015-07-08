package lt.vkk.tvarkarastis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    //private DatabaseHelper db;
    // --------------------------------VARIABLES---------------------------------------------

    protected static final String DATABASE_NAME = "tvarkarastis";
    protected static final int DATABASE_VERSION = 1;

    /**
     * Table Grupe.
     **/
    private static final String grupeTable = "grupe";
    private static final String colGrupeID = "grupeID";
    private static final String colGrupePavadinimas = "pavadinimas";
    private static final String CREATE_TABLE_GRUPE =
            "CREATE TABLE " + grupeTable + "("
                    + colGrupeID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
                    + colGrupePavadinimas + " VARCHAR NOT NULL  DEFAULT (NULL)"
                    + ")";
    String INDEX = "CREATE UNIQUE INDEX locations_index ON "
            + grupeTable + " ("+colGrupePavadinimas+")";

    public static String getGrupeTable() {
        return grupeTable;
    }
    public static String getColGrupePavadinimas() {
        return colGrupePavadinimas;
    }

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

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            // db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public void onCreate(SQLiteDatabase db) {
        // Create tables with schemas.
        db.execSQL(CREATE_TABLE_GRUPE);
        db.execSQL(INDEX);
        db.execSQL(CREATE_TABLE_DESTYTOJAS);
        db.execSQL(CREATE_TABLE_DALYKAS);
        //db.close();
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

    public void insertGrupeWithID(int id, String pavadinimas) {
        // you can use INSERT only
        String sql = "INSERT OR REPLACE INTO " + grupeTable + " VALUES(?,?)";
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


        stmt.bindLong(1, id);
        stmt.bindString(2, pavadinimas);

        stmt.execute();
        stmt.clearBindings();

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        Log.w("aliusa", "įdėta \"" + pavadinimas + "\" grupė į sqlite");
    }

    public void insertDestytojasWithID(int id, String vardas, String pavarde) {
        // you can use INSERT only
        String sql = "INSERT OR REPLACE INTO " + destytojasTable + " VALUES(?,?,?)";
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


        stmt.bindLong(1, id);
        stmt.bindString(2, vardas);
        stmt.bindString(3, pavarde);

        stmt.execute();
        stmt.clearBindings();

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        Log.w("aliusa", "įdėtas dėstytojas \"" + vardas +" "+ pavarde + "\" į sqlite");
    }
}
