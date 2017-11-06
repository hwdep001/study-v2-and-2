package com.howoh.studyv2.studyv2_2.service;

import com.howoh.studyv2.studyv2_2.vo.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBCategory {

    public static final String TABLE_NAME = "category";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUM = "num";
    private static final String COLUMN_SUBJECT_ID = "subjectId";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " VARCHAR(32),"
                    + COLUMN_NAME + " VARCHAR(32),"
                    + COLUMN_NUM + " INT2,"
                    + COLUMN_SUBJECT_ID + " VARCHAR(32),"
                    + " PRIMARY KEY (" + COLUMN_ID + ")"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_NAME + "," + COLUMN_NUM + ", " + COLUMN_SUBJECT_ID + ")"
                    + " VALUES (?, ?, ?, ? )";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM + ", " + COLUMN_SUBJECT_ID
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_NUM + " ASC";
}
