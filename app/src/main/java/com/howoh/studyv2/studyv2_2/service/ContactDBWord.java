package com.howoh.studyv2.studyv2_2.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.howoh.studyv2.studyv2_2.vo.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class ContactDBWord {

    public static final String TABLE_NAME = "word";

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
                    + COLUMN_LECTURE_ID + " VARCHAR(32), "
                    + COLUMN_LEVEL_ID + " INT2 DEFAULT 0, "
                    + " PRIMARY KEY (" + COLUMN_ID + "), "
                    + " FOREIGN KEY (" + COLUMN_LECTURE_ID + ") REFERENCES "
                    + ContactDBLecture.TABLE_NAME + "(" + ContactDBLecture.COLUMN_ID + ") ON DELETE CASCADE, "
                    + " FOREIGN KEY (" + COLUMN_LEVEL_ID + ") REFERENCES "
                    + ContactDBLevel.TABLE_NAME + "(" + ContactDBLevel.COLUMN_ID + ") ON DELETE SET DEFAULT "
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INSERT =
            "INSERT INTO " + TABLE_NAME
                    + " (" + COLUMN_ID + "," + COLUMN_HEAD1 + "," + COLUMN_HEAD2 + ", " + COLUMN_BODY1 + ", "
                    + COLUMN_BODY2 + ", " + COLUMN_NUM + ", " + COLUMN_LECTURE_ID + ", " + COLUMN_LEVEL_ID + ")"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ? )";

    public static final String UPDATE =
            "UPDATE " + TABLE_NAME
                    + " SET " + COLUMN_HEAD1 + "=?, " + COLUMN_HEAD2 + "=?, " + COLUMN_BODY1 + "=?, " + COLUMN_BODY2 + "=?, "
                        + COLUMN_NUM + "=?, " + COLUMN_LECTURE_ID + "=?, " + COLUMN_LEVEL_ID + "=? "
                    + " WHERE " + COLUMN_ID + "=? ";

    public static final String UPDATE_WITHOUT_LEVEL =
            "UPDATE " + TABLE_NAME
                    + " SET " + COLUMN_HEAD1 + "=?, " + COLUMN_HEAD2 + "=?, " + COLUMN_BODY1 + "=?, " + COLUMN_BODY2 + "=?, "
                    + COLUMN_NUM + "=?, " + COLUMN_LECTURE_ID + "=? "
                    + " WHERE " + COLUMN_ID + "=? ";

    public static final String DELETE_BY_ID =
            "DELETE FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_BY_ID =
            "SELECT " + COLUMN_ID + ", " + COLUMN_HEAD1 + ", " + COLUMN_HEAD2 + ", " + COLUMN_BODY1 + ", "
                    + COLUMN_BODY2 + ", " + COLUMN_NUM + ", " + COLUMN_LECTURE_ID + ", " + COLUMN_LEVEL_ID
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + "=?";

    public static final String SELECT_BY_LECTURE =
            "SELECT " + COLUMN_ID + ", " + COLUMN_HEAD1 + ", " + COLUMN_HEAD2 + ", " + COLUMN_BODY1 + ", "
                    + COLUMN_BODY2 + ", " + COLUMN_NUM + ", " + COLUMN_LECTURE_ID + ", " + COLUMN_LEVEL_ID
                    + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_LECTURE_ID + "=? "
                    + " ORDER BY " + COLUMN_NUM + " ASC";

    public static final String SELECT_ALL =
            "SELECT " + COLUMN_ID + ", " + COLUMN_HEAD1 + ", " + COLUMN_HEAD2 + ", " + COLUMN_BODY1 + ", "
                    + COLUMN_BODY2 + ", " + COLUMN_NUM + ", " + COLUMN_LECTURE_ID + ", " + COLUMN_LEVEL_ID
                    + " FROM " + TABLE_NAME
                    + " ORDER BY " + COLUMN_LECTURE_ID + " ASC, " + COLUMN_NUM + " ASC";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void insert(SQLiteDatabase db, Word item) {
        db.execSQL(INSERT, new Object[] {item.getId(), item.getHead1(), item.getHead2(), item.getBody1(),
            item.getBody2(), item.getNum(), item.getLectureId(), item.getLevelId()});
        db.close();
    }

    public static void update(SQLiteDatabase db, Word item) {
        db.execSQL(UPDATE, new Object[] {item.getHead1(), item.getHead2(), item.getBody1(),
                item.getBody2(), item.getNum(), item.getLectureId(), item.getLevelId(), item.getId()});
        db.close();
    }

    public static void updateWithOutLevel(SQLiteDatabase db, Word item) {
        db.execSQL(UPDATE_WITHOUT_LEVEL, new Object[] {item.getHead1(), item.getHead2(), item.getBody1(),
                item.getBody2(), item.getNum(), item.getLectureId(), item.getId()});
        db.close();
    }

    public static void delete(SQLiteDatabase db, String id) {
        db.execSQL(DELETE_BY_ID, new String[] {id});
        db.close();
    }

    public static Word getById(SQLiteDatabase db, String id) {
        Word result = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_BY_ID, new String[] {id});
            while (cursor.moveToNext()) {
                result = new Word();
                result.setId(cursor.getString(0));
                result.setHead1(cursor.getString(1));
                result.setHead2(cursor.getString(2));
                result.setBody1(cursor.getString(3));
                result.setBody2(cursor.getString(4));
                result.setNum(cursor.getInt(5));
                result.setLectureId(cursor.getString(6));
                result.setLevelId(cursor.getInt(7));
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static List<Word> getAllByLecture(SQLiteDatabase db, String lectureId) {
        List<Word> result = new ArrayList<>();
        Word item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_BY_LECTURE, new String[] {lectureId});
            while (cursor.moveToNext()) {
                item = new Word();
                item.setId(cursor.getString(0));
                item.setHead1(cursor.getString(1));
                item.setHead2(cursor.getString(2));
                item.setBody1(cursor.getString(3));
                item.setBody2(cursor.getString(4));
                item.setNum(cursor.getInt(5));
                item.setLectureId(cursor.getString(6));
                item.setLevelId(cursor.getInt(7));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static List<Word> getAll(SQLiteDatabase db) {
        List<Word> result = new ArrayList<>();
        Word item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SELECT_ALL, null);
            while (cursor.moveToNext()) {
                item = new Word();
                item.setId(cursor.getString(0));
                item.setHead1(cursor.getString(1));
                item.setHead2(cursor.getString(2));
                item.setBody1(cursor.getString(3));
                item.setBody2(cursor.getString(4));
                item.setNum(cursor.getInt(5));
                item.setLectureId(cursor.getString(6));
                item.setLevelId(cursor.getInt(7));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }
}
