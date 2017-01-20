package com.rappi.test.rappi_test;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rappi.test.rappi_test.model.Theme;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.sql.SQLException;


public class MainActivity extends ActionBarActivity {

    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isOnline()) {
            new LoadData(this).execute();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!isOnline()){
            try {
                Intent themeListIntent = new Intent(getApplicationContext(), ThemeListActivity.class);
                themeListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(themeListIntent);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class LoadData extends AsyncTask<Nullable, Integer, Nullable>{

        Context context;
        public LoadData(Context context){
            this.context = context;
        }

        @Override
        protected Nullable doInBackground(Nullable... params) {

            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            mProgress = (ProgressBar) findViewById(R.id.progressBar);

            String data_url = getString(R.string.data_url);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,data_url,null,new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        Log.i(ThemeApp.TAG, jsonObject.toString());
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        Log.i(ThemeApp.TAG, dataObject.toString());
                        JSONArray st = dataObject.getJSONArray("children");
                        for (int i=0; i<st.length(); i++){
                            JSONObject currentChild = st.getJSONObject(i);
                            JSONObject currentChildData = currentChild.getJSONObject("data");
                            if(! ((ThemeApp) getApplication()).themeExists(currentChildData.getString("id"))){
                                Theme newTheme = new Theme();
                                newTheme.id = currentChildData.getString("id");
                                newTheme.display_name = currentChildData.getString("display_name");
                                Long date = currentChildData.getLong("created");
                                newTheme.creation_date = date.toString();
                                newTheme.subscribers = currentChildData.getString("subscribers");
                                newTheme.language = currentChildData.getString("lang");
                                newTheme.color = currentChildData.getString("key_color");
                                newTheme.public_description = currentChildData.getString("description");

                                if(currentChildData.getString("icon_img")!= "" && currentChildData.getString("icon_img")!=null) {
                                    file_download(currentChildData.getString("icon_img"), currentChildData.getString("id"));
                                    newTheme.icon = "/rappi_files/icons/" + currentChildData.getString("id") + ".png";
                                }else{
                                    newTheme.icon = "";
                                }
                                //newTheme.image = currentChildData.getString("header_img");
                                try {
                                    ((ThemeApp) getApplication()).getHelper().getThemeDao().create(newTheme);
                                    Log.i(ThemeApp.TAG, "Theme Created Successfully");
                                }catch(SQLException e){
                                    Log.e(ThemeApp.TAG, e.getMessage());
                                }
                            }
                            onProgressUpdate((i + 1) * 100 / st.length());
                        }
                        Log.i(ThemeApp.TAG, String.valueOf(st.length()));
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    onPostExecute(0);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(ThemeApp.TAG,volleyError.toString());
                    Log.e(ThemeApp.TAG, volleyError.getMessage());
                    volleyError.printStackTrace();
                }
            });

            queue.add(request);

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Integer result) {
            try {
                Intent themeListIntent = new Intent(getApplicationContext(), ThemeListActivity.class);
                themeListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(themeListIntent);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public void file_download(String uRl, String themeId) {
            File direct = new File(Environment.getExternalStorageDirectory()
                    + "/rappi_files/icons");
            if (!direct.exists()) {
                direct.mkdirs();
            }

            File icon = new File(Environment.getExternalStorageDirectory()
                    + "/rappi_files/icons/"+themeId+".png");
            Log.i("RAPPI TEST","URL:"+uRl);
            Log.i("RAPPI TEST",icon.toString());
            if(!icon.exists() && !uRl.isEmpty() && uRl != null) {
                DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                Uri downloadUri = Uri.parse(uRl);
                DownloadManager.Request request = new DownloadManager.Request(
                        downloadUri);

                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false).setTitle("Demo")
                        .setDescription("Something useful. No, really.")
                        .setDestinationInExternalPublicDir("/rappi_files/icons", themeId + ".png");

                mgr.enqueue(request);
            }

        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void setProgressPercent(final Integer progres) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress(progres);
            }
        });
    }
}
