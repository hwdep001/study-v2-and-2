package com.howoh.studyv2.studyv2_2.service;

import com.howoh.studyv2.studyv2_2.vo.Count;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBCount {

    public static final String TABLE_NAME = "count";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DEFAULT = "df";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " INT,"
                    + COLUMN_DEFAULT + " boolean,"
                    + " PRIMARY KEY (" + COLUMN_ID + ")"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_DEFAULT + ")"
                    + " VALUES (?, ?, ? )";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_DEFAULT
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_ID + " ASC";

    public static List<Count> getDefaultCountList() {
        List<Count> list = new ArrayList<>();
        for(int i=10; i<=100; i++) {
            if(i==20) {
                list.add(new Count(i, true));
            }else {
                list.add(new Count(i, false));
            }
        }

        return list;
    }
}
