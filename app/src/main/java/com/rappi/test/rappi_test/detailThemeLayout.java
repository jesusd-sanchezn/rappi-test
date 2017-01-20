package com.rappi.test.rappi_test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rappi.test.rappi_test.model.Theme;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class detailThemeLayout extends ActionBarActivity {
    public Theme selectedTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_theme_layout);
        customizeToolbar();
        Intent i = getIntent();
        selectedTheme = (Theme)i.getSerializableExtra("selectedTheme");
        loadData(selectedTheme);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_theme_layout, menu);
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

    private void loadData(Theme theme){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        long dv = Long.valueOf(theme.creation_date.toString())*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd/MM/yyyy").format(df);

        TextView display_name = (TextView) findViewById(R.id.txtDisplayName);
        display_name.setText(theme.display_name);
        TextView creationDate = (TextView)findViewById(R.id.txtDate);
        creationDate.setText("Fecha: "+vv);
        TextView subscribers = (TextView)findViewById(R.id.txtSubs);
        subscribers.setText("Seguidores: "+formatter.format(Double.parseDouble(theme.subscribers)));
        TextView language = (TextView)findViewById(R.id.txtLang);
        language.setText("Idioma: "+theme.language);
        TextView color = (TextView)findViewById(R.id.txtColor);
        color.setText("Color: "+theme.color);
        TextView description = (TextView)findViewById(R.id.txtDescription);
        description.setText(theme.public_description);

        ImageView iconImage = (ImageView)findViewById(R.id.imgDetail);
        File icon = new File(Environment.getExternalStorageDirectory()
                + "/rappi_files/icons/"+theme.id+".png");
        if(icon.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/rappi_files/icons/" + theme.id + ".png");
            iconImage.setImageBitmap(bmp);
        }
    }

    private void customizeToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.rappi_detail_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Details of Theme");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    onBackPressed();
               }
           }

        );
    }
}
