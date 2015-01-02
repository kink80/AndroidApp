package myapp.com.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import myapp.com.myapplication.data.WordReaderDbHelper;
import myapp.com.myapplication.data.WordsDatabaseContract;


public class AuthorActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_author, menu);
        return true;
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

    public void finishAuthoring(View view) {
        saveCurrentWord(view);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addAnother(View view) {
        saveCurrentWord(view);

        Intent intent = new Intent(this, AuthorActivity.class);
        startActivity(intent);
    }

    private void saveCurrentWord(View view) {
        EditText original = (EditText) findViewById(R.id.original);
        EditText translation = (EditText) findViewById(R.id.translation);

        WordReaderDbHelper dbHelper = new WordReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordsDatabaseContract.WordEntry.COLUMN_NAME_ORIGINAL, original.getText().toString());
        values.put(WordsDatabaseContract.WordEntry.COLUMN_NAME_TRANSLATION, translation.getText().toString());
        values.put(WordsDatabaseContract.WordEntry.COLUMN_NAME_PROBABILITY, 50);

        long newRowId;

        newRowId = db.insert(
                WordsDatabaseContract.WordEntry.TABLE_NAME,
                null,
                values);

        Log.d("custom", String.valueOf(newRowId));
        db.close();
    }
}
