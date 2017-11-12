package com.howoh.studyv2.studyv2_2.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.howoh.studyv2.studyv2_2.vo.Lecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBLecture {

    public static final String TABLE_NAME = "lecture";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUM = "num";
    public static final String COLUMN_VERSION = "version";
    public static final String COLUMN_CATEGORY_ID = "categoryId";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " VARCHAR(32),"
                    + COLUMN_NAME + " VARCHAR(32),"
                    + COLUMN_NUM + " INT2,"
                    + COLUMN_VERSION + " INT8,"
                    + COLUMN_CATEGORY_ID + " VARCHAR(32), "
                    + " PRIMARY KEY (" + COLUMN_ID + "), "
                    + " FOREIGN KEY (" + COLUMN_CATEGORY_ID + ") REFERENCES "
                    + ContactDBCategory.TABLE_NAME + "(" + ContactDBCategory.COLUMN_ID + ") ON DELETE CASCADE"
                    + " )";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_NAME + "," + COLUMN_NUM + "," + COLUMN_VERSION + ", " + COLUMN_CATEGORY_ID + ")"
                    + " VALUES (?, ?, ?, ?, ? )";

    public static final String UPDATE =
            "UPDATE " + TABLE_NAME
                    + " SET " + COLUMN_NAME + "=?, " + COLUMN_NUM + "=?, " + COLUMN_VERSION + "=?, " + COLUMN_CATEGORY_ID + "=? "
                    + " WHERE " + COLUMN_ID + "=? ";

    public static final String DELETE_BY_ID =
            "DELETE FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_BY_ID =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM + ", " + COLUMN_VERSION + ", " + COLUMN_CATEGORY_ID
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_BY_CATEGORY =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM + ", " + COLUMN_VERSION + ", " + COLUMN_CATEGORY_ID
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_CATEGORY_ID + "=? "
                    + " ORDER BY " + COLUMN_NUM + " ASC";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM + ", " + COLUMN_VERSION + ", " + COLUMN_CATEGORY_ID
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_CATEGORY_ID + " ASC, " + COLUMN_NUM + " ASC";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void insert(SQLiteDatabase db, Lecture item) {
        db.execSQL(INSERT, new Object[] {item.getId(), item.getName(), item.getNum(), item.getVersion(), item.getCategoryId()});
        db.close();
    }

    public static void update(SQLiteDatabase db, Lecture item) {
        db.execSQL(UPDATE, new Object[] {item.getName(), item.getNum(), item.getVersion(), item.getCategoryId(), item.getId()});
        db.close();
    }

    public static void delete(SQLiteDatabase db, String id) {
        db.execSQL(DELETE_BY_ID, new String[] {id});
        db.close();
    }

    public static Lecture getById(SQLiteDatabase db, String id) {
        Lecture result = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_BY_ID, new String[] {id});
            while (cursor.moveToNext()) {
                result = new Lecture();
                result.setId(cursor.getString(0));
                result.setName(cursor.getString(1));
                result.setNum(cursor.getInt(2));
                result.setVersion(cursor.getInt(3));
                result.setCategoryId(cursor.getString(4));
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static List<Lecture> getAllByCategory(SQLiteDatabase db, String categoryId) {
        List<Lecture> result = new ArrayList<>();
        Lecture item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_BY_CATEGORY, new String[] {categoryId});
            while (cursor.moveToNext()) {
                item = new Lecture();
                item.setId(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setNum(cursor.getInt(2));
                item.setVersion(cursor.getInt(3));
                item.setCategoryId(cursor.getString(4));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static List<Lecture> getAll(SQLiteDatabase db) {
        List<Lecture> result = new ArrayList<>();
        Lecture item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_ALL, null);
            while (cursor.moveToNext()) {
                item = new Lecture();
                item.setId(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setNum(cursor.getInt(2));
                item.setVersion(cursor.getInt(3));
                item.setCategoryId(cursor.getString(4));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }
}
