package myapp.com.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stecl on 22/12/14.
 */
public class WordReaderDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WordsDatabaseContract.WordEntry.TABLE_NAME + " (" +
                    WordsDatabaseContract.WordEntry._ID + " INTEGER PRIMARY KEY," +
                    WordsDatabaseContract.WordEntry.COLUMN_NAME_ORIGINAL + TEXT_TYPE + COMMA_SEP +
                    WordsDatabaseContract.WordEntry.COLUMN_NAME_TRANSLATION + TEXT_TYPE + COMMA_SEP +
                    WordsDatabaseContract.WordEntry.COLUMN_NAME_LEVEL + INTEGER_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WordsDatabaseContract.WordEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WordReader.db";


    public WordReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
