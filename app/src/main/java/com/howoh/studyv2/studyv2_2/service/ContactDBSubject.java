package com.howoh.studyv2.studyv2_2.service;

import com.howoh.studyv2.studyv2_2.vo.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBSubject {

    public static final String TABLE_NAME = "subject";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUM = "num";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " VARCHAR(32),"
                    + COLUMN_NAME + " VARCHAR(32),"
                    + COLUMN_NUM + " INT2,"
                    + " PRIMARY KEY (" + COLUMN_ID + ")"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_NAME + "," + COLUMN_NUM + ")"
                    + " VALUES (?, ?, ? )";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_NUM + " ASC";

    public static List<Subject> getDefaultSubjectList() {
        List<Subject> list = new ArrayList<>();
        list.add(new Subject("ew", "영단어", 1));
        list.add(new Subject("lw", "외래어", 2));
        list.add(new Subject("c4", "한자성어", 3));
        list.add(new Subject("cc", "한자", 4));

        return list;
    }
}
