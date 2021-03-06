package myapp.com.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import myapp.com.myapplication.data.DataProvider;
import myapp.com.myapplication.data.InMemoryprovider;
import myapp.com.myapplication.data.WordBean;
import myapp.com.myapplication.data.WordReaderDbHelper;
import myapp.com.myapplication.data.WordsDatabaseContract;


public class LearnActivity extends ActionBarActivity {

    private SQLiteDatabase readableDb;
    private SQLiteDatabase writableDb;
    private Cursor c;
    private long currentId = -1L;
    private String currentText =  "";

    private DataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        /*
        WordReaderDbHelper dbHelper = new WordReaderDbHelper(this);
        readableDb = dbHelper.getReadableDatabase();

        String[] projection = {
                WordsDatabaseContract.WordEntry._ID,
                WordsDatabaseContract.WordEntry.COLUMN_NAME_ORIGINAL,
                WordsDatabaseContract.WordEntry.COLUMN_NAME_TRANSLATION
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                WordsDatabaseContract.WordEntry.COLUMN_NAME_PROBABILITY + " DESC";

        c = readableDb.query(
                WordsDatabaseContract.WordEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        TextView word = (TextView) findViewById(R.id.word);
        if (c != null && c.moveToFirst()) {
            currentText = c.getString(c.getColumnIndexOrThrow(WordsDatabaseContract.WordEntry.COLUMN_NAME_ORIGINAL));
            currentId = c.getLong(c.getColumnIndexOrThrow(WordsDatabaseContract.WordEntry._ID));
            word.setText(currentText);
        } else {
            word.setText("Empty");
        }

        */

        provider = new InMemoryprovider();
        provider.load();

        TextView word = (TextView) findViewById(R.id.word);

        WordBean wordBean = provider.getNext();
        if (wordBean.isValid()) {
            word.setText(wordBean.getWord());
        } else {
            word.setText("Empty");
        }
    }

    @Override
    protected void onDestroy() {
        if(readableDb != null) {
            readableDb.close();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void rate(View view) {
        String tag = String.valueOf(view.getTag());
        provider.rateCurrent(tag);

        TextView word = (TextView) findViewById(R.id.word);
        WordBean wordBean = provider.getNext();
        if (wordBean.isValid()) {
            word.setText(wordBean.getWord());
        } else {
            word.setText("Empty");
        }

        /*
        if(c != null && c.moveToNext()) {
            String first = c.getString(c.getColumnIndexOrThrow(WordsDatabaseContract.WordEntry.COLUMN_NAME_ORIGINAL));
            currentId = c.getLong(c.getColumnIndexOrThrow(WordsDatabaseContract.WordEntry._ID));
            word.setText(first);
        } else {
            word.setText("Empty");
        }
        */

    }
}
