package com.howoh.studyv2.studyv2_2.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.howoh.studyv2.studyv2_2.vo.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBLevel {

    public static final String TABLE_NAME = "level";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " INT2,"
                    + COLUMN_NAME + " VARCHAR(16),"
                    + " PRIMARY KEY (" + COLUMN_ID + ")"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_NAME + ")"
                    + " VALUES (?, ? )";

    public static final String UPDATE =
            "UPDATE " + TABLE_NAME
                    + " SET " + COLUMN_NAME + "=? "
                    + " WHERE " + COLUMN_ID + "=? ";

    public static final String DELETE_BY_ID =
            "DELETE FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_BY_ID =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_ID + " ASC";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void insert(SQLiteDatabase db, Level item) {
        db.execSQL(INSERT, new Object[] {item.getId(), item.getName()});
        db.close();
    }

    public static void update(SQLiteDatabase db, Level item) {
        db.execSQL(UPDATE, new Object[] {item.getName(), item.getId()});
        db.close();
    }

    public static void delete(SQLiteDatabase db, int id) {
        db.execSQL(DELETE_BY_ID, new Object[] {id});
        db.close();
    }

    public static Level getById(SQLiteDatabase db, int id) {
        Level result = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_BY_ID, new String[] {String .valueOf(id)});
            while (cursor.moveToNext()) {
                result = new Level();
                result.setId(cursor.getInt(0));
                result.setName(cursor.getString(1));
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static List<Level> getAll(SQLiteDatabase db) {
        List<Level> result = new ArrayList<>();
        Level item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_ALL, null);
            while (cursor.moveToNext()) {
                item = new Level();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }
}
