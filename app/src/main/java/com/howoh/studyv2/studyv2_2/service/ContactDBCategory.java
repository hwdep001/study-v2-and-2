package com.howoh.studyv2.studyv2_2.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.howoh.studyv2.studyv2_2.vo.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBCategory {

    public static final String TABLE_NAME = "category";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUM = "num";
    public static final String COLUMN_VERSION = "version";
    public static final String COLUMN_SUBJECT_ID = "subjectId";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " VARCHAR(32),"
                    + COLUMN_NAME + " VARCHAR(32),"
                    + COLUMN_NUM + " INT2,"
                    + COLUMN_VERSION + " INT8,"
                    + COLUMN_SUBJECT_ID + " VARCHAR(32), "
                    + " PRIMARY KEY (" + COLUMN_ID + "), "
                    + " FOREIGN KEY (" + COLUMN_SUBJECT_ID + ") REFERENCES "
                    + ContactDBSubject.TABLE_NAME + "(" + ContactDBSubject.COLUMN_ID + ") ON DELETE CASCADE"
                    + " )";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_NAME + "," + COLUMN_NUM + "," + COLUMN_VERSION + ", " + COLUMN_SUBJECT_ID + ")"
                    + " VALUES (?, ?, ?, ?, ? )";

    public static final String UPDATE =
            "UPDATE " + TABLE_NAME
                    + " SET " + COLUMN_NAME + "=?, " + COLUMN_NUM + "=?, " + COLUMN_VERSION + "=?, " + COLUMN_SUBJECT_ID + "=? "
                    + " WHERE " + COLUMN_ID + "=? ";

    public static final String DELETE_BY_ID =
            "DELETE FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_BY_ID =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM + ", " + COLUMN_VERSION + ", " + COLUMN_SUBJECT_ID
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_BY_SUBJECT =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM + ", " + COLUMN_VERSION + ", " + COLUMN_SUBJECT_ID
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_SUBJECT_ID + "=? "
                    + " ORDER BY " + COLUMN_NUM + " ASC";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUM + ", " + COLUMN_VERSION + ", " + COLUMN_SUBJECT_ID
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_SUBJECT_ID + " ASC, " + COLUMN_NUM + " ASC";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void insert(SQLiteDatabase db, Category item) {
        db.execSQL(INSERT, new Object[] {item.getId(), item.getName(), item.getNum(), item.getVersion(), item.getSubjectId()});
        db.close();
    }

    public static void update(SQLiteDatabase db, Category item) {
        db.execSQL(UPDATE, new Object[] {item.getName(), item.getNum(), item.getVersion(), item.getSubjectId(), item.getId()});
        db.close();
    }

    public static void delete(SQLiteDatabase db, String id) {
        db.execSQL(DELETE_BY_ID, new String[] {id});
        db.close();
    }

    public static Category getById(SQLiteDatabase db, String id) {
        Category result = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_BY_ID, new String[] {id});
            while (cursor.moveToNext()) {
                result = new Category();
                result.setId(cursor.getString(0));
                result.setName(cursor.getString(1));
                result.setNum(cursor.getInt(2));
                result.setVersion(cursor.getInt(3));
                result.setSubjectId(cursor.getString(4));
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static List<Category> getAllBySubject(SQLiteDatabase db, String subjectId) {
        List<Category> result = new ArrayList<>();
        Category item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_BY_SUBJECT, new String[] {subjectId});
            while (cursor.moveToNext()) {
                item = new Category();
                item.setId(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setNum(cursor.getInt(2));
                item.setVersion(cursor.getInt(3));
                item.setSubjectId(cursor.getString(4));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static List<Category> getAll(SQLiteDatabase db) {
        List<Category> result = new ArrayList<>();
        Category item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_ALL, null);
            while (cursor.moveToNext()) {
                item = new Category();
                item.setId(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setNum(cursor.getInt(2));
                item.setVersion(cursor.getInt(3));
                item.setSubjectId(cursor.getString(4));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }
}
