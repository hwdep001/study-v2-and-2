package com.howoh.studyv2.studyv2_2.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.howoh.studyv2.studyv2_2.vo.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBSubject {

    public static final String TABLE_NAME = "subject";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUM = "num";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " VARCHAR(32),"
                    + COLUMN_NAME + " VARCHAR(32),"
                    + COLUMN_NUM + " INT2,"
                    + " PRIMARY KEY (" + COLUMN_ID + ")"
                    + " )";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_NAME + "," + COLUMN_NUM + ")"
                    + " VALUES (?, ?, ? )";

    public static final String UPDATE =
            "UPDATE " + TABLE_NAME
                    + " SET " + COLUMN_NAME + "=?, " + COLUMN_NUM + "=? "
                    + " WHERE " + COLUMN_ID + "=? ";

    public static final String SELECT_BY_ID =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_NUM + " ASC";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void insert(SQLiteDatabase db, Subject item) {
        db.execSQL(INSERT, new Object[] {item.getId(), item.getName(), item.getNum()});
        db.close();
    }

    public static void update(SQLiteDatabase db, Subject item) {
        db.execSQL(UPDATE, new Object[] {item.getName(), item.getNum(), item.getId()});
        db.close();
    }

    public static Subject getById(SQLiteDatabase db, String id) {
        Subject result = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_BY_ID, new String[] {id});
            while (cursor.moveToNext()) {
                result = new Subject();
                result.setId(cursor.getString(0));
                result.setName(cursor.getString(1));
                result.setNum(cursor.getInt(2));
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static List<Subject> getAll(SQLiteDatabase db) {
        List<Subject> result = new ArrayList<>();
        Subject item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_ALL, null);
            while (cursor.moveToNext()) {
                item = new Subject();
                item.setId(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setNum(cursor.getInt(2));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }
}
