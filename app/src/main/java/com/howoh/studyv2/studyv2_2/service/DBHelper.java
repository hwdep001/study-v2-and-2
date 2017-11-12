package com.howoh.studyv2.studyv2_2.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.howoh.studyv2.studyv2_2.vo.Category;
import com.howoh.studyv2.studyv2_2.vo.Lecture;
import com.howoh.studyv2.studyv2_2.vo.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-05.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();
    private static DBHelper db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "study.db";

    public static final String EXIST_TABLE =
            "SELECT name FROM sqlite_master WHERE type='table' AND name= ?";

    public static DBHelper getInstance(Context context) {
        if(db == null) {
            db = new DBHelper(context);
        }
        return db;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        String query = String.format ("PRAGMA foreign_keys = %s","ON");
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactDBSubject.CREATE_TABLE);
        db.execSQL(ContactDBCategory.CREATE_TABLE);
        db.execSQL(ContactDBLecture.CREATE_TABLE);
        Log.d(TAG, "[test]-onCreate");
    }

    public void dropTable(SQLiteDatabase db) {
        db.execSQL(ContactDBSubject.DROP_TABLE);
        db.execSQL(ContactDBCategory.DROP_TABLE);
        db.execSQL(ContactDBLecture.DROP_TABLE);
        Log.d(TAG, "[test]-dropTable");
    }

    public boolean isExistTable(SQLiteDatabase db, String tableName) {
        boolean result = false;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(EXIST_TABLE, new String[] {tableName});
            if(cursor.moveToNext()) {
                result = true;
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SUBJECT
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void insertSubject(Subject subject) {
        ContactDBSubject.insert(getWritableDatabase(), subject);
    }

    public void updateSubject(Subject subject) {
        ContactDBSubject.update(getWritableDatabase(), subject);
    }

    public Subject getSubject(String id) {
        return ContactDBSubject.getById(getWritableDatabase(), id);
    }

    public List<Subject> getSubjecs() {
        return ContactDBSubject.getAll(getWritableDatabase());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CATEGORY
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void insertCategory(Category category) {
        ContactDBCategory.insert(getWritableDatabase(), category);
    }

    public void insertCategoryWithOutVersion(Category category) {
        category.setVersion(-1);
        ContactDBCategory.insert(getWritableDatabase(), category);
    }

    public void updateCategory(Category category) {
        ContactDBCategory.update(getWritableDatabase(), category);
    }

    public void updateCategoryWithOutVersion(Category category) {
        category.setVersion(-1);
        ContactDBCategory.update(getWritableDatabase(), category);
    }

    public void deleteCategory(String id) {
        ContactDBCategory.delete(getWritableDatabase(), id);
    }

    public Category getCategory(String id) {
        return ContactDBCategory.getById(getWritableDatabase(), id);
    }

    public List<Category> getCategories(String subjectId) {
        return ContactDBCategory.getAllBySubject(getWritableDatabase(), subjectId);
    }

    public List<Category> getCategories() {
        return ContactDBCategory.getAll(getWritableDatabase());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LECTURE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void insertLecture(Lecture lecture) {
        ContactDBLecture.insert(getWritableDatabase(), lecture);
    }

    public void insertLectureWithOutVersion(Lecture lecture) {
        lecture.setVersion(-1);
        ContactDBLecture.insert(getWritableDatabase(), lecture);
    }

    public void updateLectureWithOutVersion(Lecture lecture) {
        lecture.setVersion(-1);
        ContactDBLecture.update(getWritableDatabase(), lecture);
    }

    public void updateLecture(Lecture lecture) {
        ContactDBLecture.update(getWritableDatabase(), lecture);
    }

    public void deleteLecture(String id) {
        ContactDBLecture.delete(getWritableDatabase(), id);
    }

    public Lecture getLecture(String id) {
        return ContactDBLecture.getById(getWritableDatabase(), id);
    }

    public List<Lecture> getLectures(String categoryId) {
        return ContactDBLecture.getAllByCategory(getWritableDatabase(), categoryId);
    }

    public List<Lecture> getLectures() {
        return ContactDBLecture.getAll(getWritableDatabase());
    }

}
