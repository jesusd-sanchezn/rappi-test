package com.rappi.test.rappi_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rappi.test.rappi_test.model.Theme;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ThemeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.rappi_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Themes");
        try {
            List<Theme> themes = ((ThemeApp) getApplication()).getThemes();
            ListView lv = (ListView) findViewById(R.id.themeListView);
            ThemeListAdapter arr = new ThemeListAdapter(this, R.layout.theme_row_layout, R.id.displayNameList, themes);
            lv.setAdapter(arr);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Theme entry= (Theme) parent.getAdapter().getItem(position);
                    try {
                        Intent themeDetailIntent = new Intent(getApplicationContext(), detailThemeLayout.class);
                        themeDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        themeDetailIntent.putExtra("selectedTheme", (Serializable)entry);
                        getApplicationContext().startActivity(themeDetailIntent);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            Log.i(((ThemeApp) getApplication()).TAG, themes.toString());
        }catch(Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_theme_list, menu);
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

    private static class ThemeListAdapter extends ArrayAdapter<Theme>{

        private static class ViewHolder{
            private ImageView img;
            private TextView display_name;
            private TextView subscribers;

            ViewHolder(){

            }

        }

        private final LayoutInflater inflater;

        public ThemeListAdapter(final Context context, final int resource, final int textViewResourceId, final List<Theme> objects){
            super(context, resource, textViewResourceId, objects);

            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent){
            View itemView = convertView;
            ViewHolder holder = null;
            final Theme item = getItem(position);
            DecimalFormat formatter = new DecimalFormat("#,###,###");

            if(itemView == null){
                itemView = this.inflater.inflate(R.layout.theme_row_layout, parent, false);

                holder = new ViewHolder();

                holder.img = (ImageView)itemView.findViewById(R.id.imgList);
                holder.display_name = (TextView)itemView.findViewById(R.id.displayNameList);
                holder.subscribers = (TextView)itemView.findViewById(R.id.subscribersList);

                itemView.setTag(holder);
            }else{
                holder = (ViewHolder)itemView.getTag();
            }

            File icon = new File(Environment.getExternalStorageDirectory()
                    + "/rappi_files/icons/"+item.id+".png");
            if(icon.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/rappi_files/icons/" + item.id + ".png");
                holder.img.setImageBitmap(bmp);
            }
            holder.display_name.setText(item.display_name);
            holder.subscribers.setText("Subscribers:"+formatter.format(Double.parseDouble(item.subscribers)));

            return itemView;
        }
    }
}


