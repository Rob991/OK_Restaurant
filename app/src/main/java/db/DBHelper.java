package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ristorante";
    private static final String TABLE_NAME = "prenotazioni";
    private static final int DB_VERSION = 1;
    private static final String COL_1 = "ID";
    private static final String COL_2 = "COGNOME";
    private static final String COL_3 = "DATA";
    private static final String COL_4 = "ORARIO";
    private static final String COL_5 = "NUM_PERSONE";
    private static final String COL_6 = "RICHIESTA";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT, " +
                COL_5 + " TEXT, " +
                COL_6 + " TEXT); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public boolean insertData(String cognome, String data, String orario, String numPersone, String richiesta) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, cognome);
        contentValues.put(COL_3, data);
        contentValues.put(COL_4, orario);
        contentValues.put(COL_5, numPersone);
        contentValues.put(COL_6, richiesta);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(String id, String cognome, String data, String orario, String numPersone, String richiesta) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_2, cognome);
        cv.put(COL_3, data);
        cv.put(COL_4, orario);
        cv.put(COL_5, numPersone);
        cv.put(COL_6, richiesta);
        long result = db.update(TABLE_NAME, cv, " ID = ? ", new String[]{id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteData(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{id});
        long result = db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
