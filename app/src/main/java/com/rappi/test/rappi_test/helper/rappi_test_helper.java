package com.rappi.test.rappi_test.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.rappi.test.rappi_test.model.Theme;
import com.rappi.test.rappi_test.ThemeApp;

import java.sql.SQLException;

/**
 * Created by jesus sanchez on 18/01/2017.
 */
public class rappi_test_helper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME = "themes.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Theme, Integer> themeDao = null;

    public Dao<Theme, Integer> getThemeDao() throws SQLException {
        if (themeDao == null)
            themeDao = getDao(Theme.class);
        return themeDao;
    }

    public rappi_test_helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource){
        try{
            Log.i(ThemeApp.TAG, "onCreate");
            TableUtils.createTable(connectionSource, Theme.class);
        }catch(Exception e){
            Log.e(ThemeApp.TAG, "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion){
        try {
            Log.i(ThemeApp.TAG, "onUpgrade");
            TableUtils.dropTable(connectionSource, ThemeApp.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(ThemeApp.TAG, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
