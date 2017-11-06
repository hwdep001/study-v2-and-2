package com.howoh.studyv2.studyv2_2.service;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBWord {

    public static final String TABLE_NAME = "lecture";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_HEAD1 = "head1";
    private static final String COLUMN_HEAD2 = "head2";
    private static final String COLUMN_BODY1 = "body1";
    private static final String COLUMN_BODY2 = "body2";
    private static final String COLUMN_NUM = "num";
    private static final String COLUMN_LECTURE_ID = "lectureId";
    private static final String COLUMN_LEVEL_ID = "levelId";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " VARCHAR(32),"
                    + COLUMN_HEAD1 + " VARCHAR(64),"
                    + COLUMN_HEAD2 + " VARCHAR(64),"
                    + COLUMN_BODY1 + " TEXT,"
                    + COLUMN_BODY2 + " TEXT,"
                    + COLUMN_NUM + " INT,"
                    + COLUMN_LECTURE_ID + " VARCHAR(32),"
                    + COLUMN_LEVEL_ID + " INT2,"
                    + " PRIMARY KEY (" + COLUMN_ID + ")"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_HEAD1 + "," + COLUMN_HEAD2 + ", " + COLUMN_BODY1 + ", "
                    + COLUMN_BODY2 + ", " + COLUMN_NUM + ", " + COLUMN_LECTURE_ID + ", " + COLUMN_LEVEL_ID + ")"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ? )";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_HEAD1 + ", " + COLUMN_HEAD2 + ", " + COLUMN_BODY1 + ", "
                    + COLUMN_BODY2 + ", " + COLUMN_NUM + ", " + COLUMN_LECTURE_ID + ", " + COLUMN_LEVEL_ID
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_NUM + " ASC";
}
