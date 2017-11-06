package com.howoh.studyv2.studyv2_2.service;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBLecture {

    public static final String TABLE_NAME = "lecture";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUM = "num";
    private static final String COLUMN_CATEGORY_ID = "categoryId";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " VARCHAR(32),"
                    + COLUMN_NAME + " VARCHAR(32),"
                    + COLUMN_NUM + " INT2,"
                    + COLUMN_CATEGORY_ID + " VARCHAR(32),"
                    + " PRIMARY KEY (" + COLUMN_ID + ")"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_NAME + "," + COLUMN_NUM + ", " + COLUMN_CATEGORY_ID + ")"
                    + " VALUES (?, ?, ?, ? )";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM + ", " + COLUMN_CATEGORY_ID
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_NUM + " ASC";
}
