package com.howoh.studyv2.studyv2_2.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

//    private static final String TABLE_SUBJECT = "subject";
//    private static final String TABLE_CATEGORY = "category";
//    private static final String TABLE_LECTURE = "lecture";
//    private static final String TABLE_WORD = "word";
//    private static final String TABLE_LEVEL = "level";
//    private static final String TABLE_COUNT = "count";
//
//    // Common column names
//    private static final String COLUMN_ID = "id";
//    private static final String COLUMN_NAME = "name";
//    private static final String COLUMN_NUM = "num";
//    private static final String COLUMN_DEFALUT = "default";
//
//    // subject
//
//    // category
//    private static final String COLUMN_SUBJECT_ID = "subjectId";
//
//    // lecture
//    private static final String COLUMN_CATEGORY_ID = "categoryId";
//
//    // word
//    private static final String COLUMN_HEAD1 = "head1";
//    private static final String COLUMN_HEAD2 = "head2";
//    private static final String COLUMN_BODY1 = "body1";
//    private static final String COLUMN_BODY2 = "body2";
//    private static final String COLUMN_LECTURE_ID = "lectureId";
//    private static final String COLUMN_LEVEL_ID = "levelId";


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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public void onCreate(SQLiteDatabase db) {
        initSubject(db);
    }

    public void dropTable(SQLiteDatabase db) {
        db.execSQL(ContactDBSubject.DROP_TABLE);
        Log.d(TAG, "[test]-dropTable");
    }

    private boolean isExistTable(SQLiteDatabase db, String tableName) {
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

    private void initSubject(SQLiteDatabase db) {

        boolean isExistTable = isExistTable(db, ContactDBSubject.TABLE_NAME);

        if(!isExistTable) {
            db.execSQL(ContactDBSubject.CREATE_TABLE);
            for(Subject subject : ContactDBSubject.getDefaultSubjectList()) {
                insertSubject(subject);
            }
        }

        Log.d(TAG, "[test]-initSubject");
    }


    public void insertSubject(Subject subject) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(ContactDBSubject.INSERT, new Object[] {subject.getId(), subject.getName(), subject.getNum()});
        db.close();
    }

    public List<Subject> getSubjecs() {
        SQLiteDatabase db = getWritableDatabase();
        List<Subject> result = new ArrayList<>();
        Subject item = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(ContactDBSubject.SELECT_ALL, null);
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

//    public void update(String item, int price) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("UPDATE MONEYBOOK SET price=" + price + " WHERE item='" + item + "';");
//        db.close();
//    }
//
//    public void delete(String item) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE FROM MONEYBOOK WHERE item='" + item + "';");
//        db.close();
//    }
//
//    public String getResult() {
//        // 읽기가 가능하게 DB 열기
//        SQLiteDatabase db = getReadableDatabase();
//        String result = "";
//
//        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
//        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK", null);
//        while (cursor.moveToNext()) {
//            result += cursor.getString(0)
//                    + " : "
//                    + cursor.getString(1)
//                    + " | "
//                    + cursor.getInt(2)
//                    + "원 "
//                    + cursor.getString(3)
//                    + "\n";
//        }
//
//        return result;
//    }

}
