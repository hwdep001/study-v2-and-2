package com.howoh.studyv2.studyv2_2.service;

import com.howoh.studyv2.studyv2_2.vo.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBLevel {

    public static final String TABLE_NAME = "level";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEFAULT = "df";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " INT2,"
                    + COLUMN_NAME + " VARCHAR(16),"
                    + COLUMN_DEFAULT + " boolean,"
                    + " PRIMARY KEY (" + COLUMN_ID + ")"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_NAME + "," + COLUMN_DEFAULT + ")"
                    + " VALUES (?, ?, ? )";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_DEFAULT
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_ID + " ASC";

    public static List<Level> getDefaultLevelList() {
        List<Level> list = new ArrayList<>();
        list.add(new Level(2, "Very Difficult", true));
        list.add(new Level(2, "Difficult", true));
        list.add(new Level(2, "Normal", true));
        list.add(new Level(2, "Easy", true));
        list.add(new Level(2, "Very Easy", true));

        return list;
    }
}
