package com.lmu.warungdana.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lmu.warungdana.ModelSQLite.UserLogModel;
import com.lmu.warungdana.Response.ListAlamat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "WarungDana";
    private static String DB_PATH = "";
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    // table name
    private static final String TABLE_USER_LOG = "user_log";
    public static final String TABLE_ALAMAT = "mst_address";

    // column tables
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_ID_CMS_USERS = "id_cms_users";


    private static final String KEY_ID_MODUL = "id_modul";
    private static final String KEY_ID_DATA = "id_data";
    private static final String KEY_JENIS = "jenis";


    public static final String COLUMN_KELURAHAN = "kelurahan";
    public static final String COLUMN_KECAMATAN = "kecamatan";
    public static final String COLUMN_KABUPATEN = "kabupaten";
    public static final String COLUMN_PROVINSI = "provinsi";
    public static final String COLUMN_KODEPOS = "kodepos";


    String alamat = "CREATE TABLE " + TABLE_ALAMAT
            + "(" + KEY_ID +
            " INTEGER UNIQUE, " + COLUMN_KELURAHAN +
            " VARCHAR, " + COLUMN_KECAMATAN +
            " VARCHAR, " + COLUMN_KABUPATEN +
            " VARCHAR, " + COLUMN_PROVINSI +
            " VARCHAR, " + COLUMN_KODEPOS +
            " VARCHAR);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.mContext = context;
    }

    public void createDataBase() throws IOException {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_TABLE = "CREATE TABLE " + TABLE_USER_LOG + "("
//                + KEY_ID + " INTEGER PRIMARY KEY,"
//                + KEY_CREATED_AT + " TEXT,"
//                + KEY_ID_CMS_USERS + " INTEGER,"
//                + KEY_ID_MODUL + " INTEGER,"
//                + KEY_ID_DATA + " INTEGER,"
//                + KEY_JENIS + " TEXT" + ")";
//        db.execSQL(CREATE_TABLE);
//        db.execSQL(alamat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LOG);
//        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ALAMAT);
//        onCreate(db);
    }

    public List<ListAlamat> getAlamat(String where) {
        List<ListAlamat> listAlamats = new ArrayList<>();
        String query1 = "SELECT * FROM " + TABLE_ALAMAT + where;
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor1 = db1.rawQuery(query1, null);
        if (cursor1.moveToFirst()) {
            do {
                ListAlamat listAlamat = new ListAlamat();
                listAlamat.setId(Integer.valueOf(cursor1.getString(0)));
                listAlamat.setKelurahan(cursor1.getString(1));
                listAlamat.setKecamatan(cursor1.getString(2));
                listAlamat.setKabupaten(cursor1.getString(3));
                listAlamat.setProvinsi(cursor1.getString(4));
                listAlamat.setKodepos(cursor1.getString(5));
                listAlamats.add(listAlamat);
            } while (cursor1.moveToNext());
        }
        cursor1.close();
        return listAlamats;
    }

    public List<ListAlamat> getProv() {
        List<ListAlamat> listAlamats = new ArrayList<>();
        String query1 = "SELECT DISTINCT provinsi FROM " + TABLE_ALAMAT ;
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor1 = db1.rawQuery(query1, null);
        if (cursor1.moveToFirst()) {
            do {
                ListAlamat listAlamat = new ListAlamat();
                listAlamat.setProvinsi(cursor1.getString(0));
                listAlamats.add(listAlamat);
            } while (cursor1.moveToNext());
        }
        cursor1.close();
        return listAlamats;
    }

    public void addUserLog(UserLogModel userLogModel) {
        String jam = DateFormat.getDateTimeInstance().format(new Date());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CREATED_AT, jam);
        values.put(KEY_ID_CMS_USERS, userLogModel.getId_cms_users());
        values.put(KEY_ID_MODUL, userLogModel.getId_modul());
        values.put(KEY_ID_DATA, userLogModel.getId_data());
        values.put(KEY_JENIS, userLogModel.getJenis());

        db.insert(TABLE_USER_LOG, null, values);
        db.close();
    }

    public List<UserLogModel> getUserLog() {
        List<UserLogModel> logModels = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USER_LOG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                UserLogModel userLogModel = new UserLogModel();
                userLogModel.setId(Integer.valueOf(cursor.getString(0)));
                userLogModel.setCreated_at(cursor.getString(1));
                userLogModel.setId_cms_users(Integer.valueOf(cursor.getString(2)));
                userLogModel.setId_modul(Integer.valueOf(cursor.getString(3)));
                userLogModel.setId_data(Integer.valueOf(cursor.getString(4)));
                userLogModel.setJenis(cursor.getString(5));
                logModels.add(userLogModel);
            } while (cursor.moveToNext());
        }
        return logModels;
    }

    public void hapus(ListAlamat listAlamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALAMAT, KEY_ID + "=?", new String[]{String.valueOf(listAlamat.getId())});
        db.close();
    }

}
