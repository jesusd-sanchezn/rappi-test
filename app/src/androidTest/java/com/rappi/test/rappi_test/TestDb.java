package com.rappi.test.rappi_test;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.test.AndroidTestCase;

import com.rappi.test.rappi_test.model.Theme;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.rappi.test.rappi_test.helper.rappi_test_helper;

import java.util.List;

/**
 * Created by tomas castillo on 21/01/2017.
 */
public class TestDb extends AndroidTestCase {
    private rappi_test_helper mHelper;

    //Valida que se pueda leer de la base de datos
    public void testReadFromDatabase(){

        mHelper = OpenHelperManager.getHelper(getContext(), rappi_test_helper.class);

        try {
            List<Theme> themes = mHelper.getThemeDao().queryForAll();
            assertTrue(!themes.isEmpty());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
