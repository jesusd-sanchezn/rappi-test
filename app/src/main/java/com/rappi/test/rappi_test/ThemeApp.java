package com.rappi.test.rappi_test;

import android.app.Application;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.rappi.test.rappi_test.helper.rappi_test_helper;
import com.rappi.test.rappi_test.model.Theme;

import java.util.List;

/**
 * Created by tomas castillo on 18/01/2017.
 */
public class ThemeApp extends Application {
    public static final String TAG = "Rappi Test";
    private rappi_test_helper mHelper;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public synchronized rappi_test_helper getHelper() {
        if(mHelper == null)
            mHelper = OpenHelperManager.getHelper(this, rappi_test_helper.class);
        return mHelper;
    }

    public List<Theme> getThemes() {
        try {
            List<Theme> themes = getHelper().getThemeDao().queryForAll();
            return themes;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    public Boolean themeExists(String id){
        try {
            List<Theme> theme = getHelper().getThemeDao().queryForEq(Theme.FIELD_ID, id);
            return theme.size() > 0 ? true : false;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }

}
