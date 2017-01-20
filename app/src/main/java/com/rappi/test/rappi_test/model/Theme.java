package com.rappi.test.rappi_test.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by tomas castillo on 18/01/2017.
 */
@DatabaseTable(tableName = Theme.TABLE_NAME)
public class Theme implements Serializable{

    public static final String TABLE_NAME = "themes";
    public static final String FIELD_ID = "id";
    public static final String FIELD_DISPLAY_NAME = "display_name";
    public static final String FIELD_CREATION_DATE = "creation_date";
    public static final String FIELD_SUBSCRIBERS = "subscribers";
    public static final String FIELD_LANGUAGE = "language";
    public static final String FIELD_COLOR = "color";
    public static final String FIELD_DESCRIPTION = "public_description";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_IMAGE = "image";

    @DatabaseField(columnName = FIELD_ID)
    public String id;

    @DatabaseField(columnName = FIELD_DISPLAY_NAME, index = true)
    public String display_name;

    @DatabaseField(columnName = FIELD_CREATION_DATE)
    public String creation_date;

    @DatabaseField(columnName = FIELD_SUBSCRIBERS)
    public String subscribers;

    @DatabaseField(columnName = FIELD_LANGUAGE)
    public String language;

    @DatabaseField(columnName = FIELD_COLOR)
    public String color;

    @DatabaseField(columnName = FIELD_DESCRIPTION)
    public String public_description;

    @DatabaseField(columnName = FIELD_ICON)
    public String icon;

    @DatabaseField(columnName = FIELD_IMAGE)
    public String image;


    public Theme(String display_name, String creation_date) {
        this.display_name = display_name;
        this.creation_date = creation_date;
    }

    public Theme() {

    }


}
