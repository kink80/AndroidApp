package myapp.com.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import myapp.com.myapplication.data.WordReaderDbHelper;
import myapp.com.myapplication.data.WordsDatabaseContract;


public class AuthorActivity extends ActionBarActivity {

    private Spinner translationList;
    private ProgressDialog progress;
    private static String TRANSLATION_API = "https://www.kimonolabs.com/api/7fi9xp94?apikey=42a06484ff91768fa61c0a31e8f79bac&lines=10&vcb=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        translationList = (Spinner) findViewById(R.id.translation);
        progress = new ProgressDialog(this);
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

    public void lookupWord(View view) {
        EditText original = (EditText) findViewById(R.id.original);

        new HttpAsyncTask().execute(TRANSLATION_API + original.getText());
    }

    private void saveCurrentWord(View view) {
        /*
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
        */
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            // show the progress bar
            progress.setMessage("Looking up translations");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }

        @Override
        protected JSONObject doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                List<String> transl = new ArrayList<String>();
                try {
                    JSONObject resObject = result.getJSONObject("results");
                    JSONArray translations = resObject.getJSONArray("collection1");
                    for (int i = 0; i < translations.length(); ++i) {
                        JSONObject translation = translations.getJSONObject(i);
                        String text = translation.getString("translation");
                        transl.add(text);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AuthorActivity.this,
                        R.layout.spinner_item, transl);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                translationList.setAdapter(dataAdapter);
                translationList.setSelection(1, true);
            } finally {
                progress.hide();
            }
        }
    }

    public static JSONObject GET(String url){
        InputStream inputStream = null;
        JSONObject result = new JSONObject();
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null) {
                String stringResponse = convertInputStreamToString(inputStream);
                result = new JSONObject(stringResponse);
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
