package com.denise.postres.com.co.postresnokia.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.denise.postres.com.co.postresnokia.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteDatabaseAdapter extends SQLiteOpenHelper {

    private static SQLiteDatabase sqliteDb;
    private static SQLiteDatabaseAdapter instance;
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH_PREFIX = "/data/user/0/";
    private static String DB_PATH_SUFFIX = "/databases/";
    private static final String TAG = "";
    private static String db;
    private Context context;

    /**
     * constructor
     */
    private SQLiteDatabaseAdapter(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        Log.i(TAG, "Create or Open database : " + name);

    }

    private static void initialize(Context context, String databaseName) {
        if (instance == null) {
            if (!checkDatabase(context, databaseName)) {
                try {
                    copyDataBase(context, databaseName);
                } catch (IOException e) {
                    Log.e(TAG,
                            "Database "
                                    + databaseName
                                    + " does not exists and there is no Original Version in Asset dir");
                }
            }
            Log.i(TAG, "Try to create instance of database (" + databaseName
                    + ")");
            instance = new SQLiteDatabaseAdapter(context, databaseName, null,
                    DATABASE_VERSION);
            sqliteDb = instance.getWritableDatabase();
            Log.i(TAG, "instance of database (" + databaseName + ") created !");
        }
    }

    public static final SQLiteDatabaseAdapter getInstance(Context context) {
        db = context.getString(R.string.db_name);
        initialize(context, db);
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return sqliteDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate : nothing to do");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onCreate : nothing to do");

    }

    @SuppressWarnings("unused")
    private void copyDataBase(String databaseName) throws IOException {
        copyDataBase(context, databaseName);
    }

    private static void copyDataBase(Context aContext, String databaseName)
            throws IOException {
        InputStream myInput = aContext.getAssets().open(databaseName);
        String outFileName = getDatabasePath(aContext, databaseName);
        Log.i(TAG,
                "Check if create dir : " + DB_PATH_PREFIX
                        + aContext.getPackageName() + DB_PATH_SUFFIX);
        File f = new File(DB_PATH_PREFIX + aContext.getPackageName()
                + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();
        Log.i(TAG, "Trying to copy local DB to : " + outFileName);
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.i(TAG, "DB (" + databaseName + ") copied!");
    }

    public boolean checkDatabase(String databaseName) {
        return checkDatabase(context, databaseName);
    }

    public static boolean checkDatabase(Context aContext, String databaseName) {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = getDatabasePath(aContext, databaseName);

            Log.i(TAG, "Trying to conntect to : " + myPath);
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            Log.i(TAG, "Database " + databaseName + " found!");
            checkDB.close();
        } catch (SQLiteException e) {
            Log.i(TAG, "Database " + databaseName + " does not exists!");

        }
        return checkDB != null ? true : false;
    }

    /***
     * Method that returns database path in the application's data directory
     *
     * @param databaseName : database name
     * @return : complete path
     */
    @SuppressWarnings("unused")
    private String getDatabasePath(String databaseName) {
        return getDatabasePath(context, databaseName);
    }

    /***
     * Static Method that returns database path in the application's data
     * directory
     *
     * @param aContext     : application context
     * @param databaseName : database name
     * @return : complete path
     */
    private static String getDatabasePath(Context aContext, String databaseName) {
        return DB_PATH_PREFIX + aContext.getPackageName() + DB_PATH_SUFFIX
                + databaseName;
    }
}