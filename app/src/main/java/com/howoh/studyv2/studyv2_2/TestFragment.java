package com.howoh.studyv2.studyv2_2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.howoh.studyv2.studyv2_2.service.ContactDBCategory;
import com.howoh.studyv2.studyv2_2.service.ContactDBLecture;
import com.howoh.studyv2.studyv2_2.service.ContactDBLevel;
import com.howoh.studyv2.studyv2_2.service.ContactDBSubject;
import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.vo.Category;
import com.howoh.studyv2.studyv2_2.vo.Lecture;
import com.howoh.studyv2.studyv2_2.vo.Level;
import com.howoh.studyv2.studyv2_2.vo.Subject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestFragment extends BaseFragment implements View.OnClickListener {

    private static String TAG = TestFragment.class.getSimpleName();

    private FirebaseFirestore mDb;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test, container, false);

        mDb = FirebaseFirestore.getInstance();

        dbHelper = DBHelper.getInstance(getActivity());

        Button verCheckBtn = (Button) v.findViewById(R.id.ver_check);
        Button allDelBtn = (Button) v.findViewById(R.id.all_delete);

        Button subSelectBtn = (Button) v.findViewById(R.id.btn_sub_select);
        Button catSelectBtn = (Button) v.findViewById(R.id.btn_cat_select);
        Button lecSelectBtn = (Button) v.findViewById(R.id.btn_lec_select);
        Button levSelectBtn = (Button) v.findViewById(R.id.btn_lev_select);

        allDelBtn.setOnClickListener(this);
        verCheckBtn.setOnClickListener(this);

        subSelectBtn.setOnClickListener(this);
        catSelectBtn.setOnClickListener(this);
        lecSelectBtn.setOnClickListener(this);
        levSelectBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase db = dbHelper.getReadableDatabase() ;

        switch (v.getId()) {
            case R.id.ver_check:
                checkVersion(db);
                break;
            case R.id.all_delete:
                dbHelper.dropTable(db);
                break;
            case R.id.btn_sub_select:
                if(dbHelper.isExistTable(db, ContactDBSubject.TABLE_NAME)) {
                    for(Subject subject : dbHelper.getSubjecs()) {
                        Log.d(TAG, "[test]-select subs" + subject.toString());
                    }
                }
                break;
            case R.id.btn_cat_select:
                if(dbHelper.isExistTable(db, ContactDBCategory.TABLE_NAME)) {
                    for(Category category: dbHelper.getCategories()) {
                        Log.d(TAG, "[test]-select cats: " + category.toString());
                    }
                }
                break;
            case R.id.btn_lec_select:
                if(dbHelper.isExistTable(db, ContactDBLecture.TABLE_NAME)) {
                    for(Lecture lecture: dbHelper.getLectures()) {
                        Log.d(TAG, "[test]-select lecs: " + lecture.toString());
                    }
                }
                break;
            case R.id.btn_lev_select:
                if(dbHelper.isExistTable(db, ContactDBLevel.TABLE_NAME)) {
                    for(Level level: dbHelper.getLevels()) {
                        Log.d(TAG, "[test]-select levs: " + level.toString());
                    }
                }
                break;
        }
    }

    private void checkVersion(final SQLiteDatabase db) {
        showProgressDialog(null);
        dbHelper.onCreate(db);
        checkSub();
        checkLev();
    }

    private void checkSub() {
        mDb.collection("subs").orderBy("num").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot subsDs) {

                for(DocumentSnapshot subDs: subsDs) {
                    Subject sub = subDs.toObject(Subject.class);
                    sub.setId(subDs.getId());
                    Subject sub_ = dbHelper.getSubject(sub.getId());

                    if (sub_ == null) {
                        dbHelper.insertSubject(sub);
                    } else if (!sub.equals(sub_)) {
                        dbHelper.updateSubject(sub);
                    }
                }

                // check cat
                checkCat(subsDs);
            }
        });
    }

    private void checkCat(QuerySnapshot subsDs) {
        for(final DocumentSnapshot subDs : subsDs) {
            subDs.getReference().collection("cats").orderBy("num").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot catsDs) {

                    Map<String, Category> map = new HashMap<>();

                    for(Category cat: dbHelper.getCategories(subDs.getId())) {
                        map.put(cat.getId(), cat);
                    }

                    for(DocumentSnapshot catDs: catsDs) {

                        boolean lecCheckFlag = false;

                        Category cat = catDs.toObject(Category.class);
                        cat.setId(catDs.getId());
                        cat.setSubjectId(subDs.getId());
                        Category cat_ = map.get(cat.getId());

                        Log.d(TAG, "[test]-checkCat : " + cat.getName());

                        // insert
                        if (cat_ == null) {
                            lecCheckFlag = true;
                            dbHelper.insertCategoryWithOutVersion(cat);
                            Log.d(TAG, "[test]-checkCat-insert : " + cat.getName());
                        } else {
                            map.remove(cat.getId());
                        }

                        // update
                        if(cat_ != null) {
                            if(cat.getVersion() != cat_.getVersion()) {
                                lecCheckFlag = true;
                            }

                            if (!cat.equals(cat_)) {
                                dbHelper.updateCategoryWithOutVersion(cat);
                                Log.d(TAG, "[test]-checkCat-update : " + cat.getName());
                            }
                        }

                        if(lecCheckFlag) {
                            checkLec(catDs, cat);
                        }
                    }

                    for(String key: map.keySet()) {
                        dbHelper.deleteCategory(key);
                        Log.d(TAG, "[test]-checkCat-remove : " + map.get(key).getName());
                    }
                }
            });
        }
    }

    private void checkLec(DocumentSnapshot catDs, final Category cat__) {
        Log.d(TAG, "[test]-checkLec CAT: " + cat__.getName());

        catDs.getReference().collection("lecs").orderBy("num").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot lecsDs) {

                Map<String, Lecture> map = new HashMap<>();

                for(Lecture lec: dbHelper.getLectures(cat__.getId())) {
                    map.put(lec.getId(), lec);
                }

                for(DocumentSnapshot lecDs: lecsDs) {

                    boolean wordCheckFlag = false;

                    Lecture lec = lecDs.toObject(Lecture.class);
                    lec.setId(lecDs.getId());
                    lec.setCategoryId(cat__.getId());
                    Lecture lec_ = map.get(lec.getId());

                    Log.d(TAG, "[test]-checkLec : " + lec.getName());

                    // insert
                    if (lec_ == null) {
                        wordCheckFlag = true;
                        dbHelper.insertLectureWithOutVersion(lec);
                        Log.d(TAG, "[test]-checkLec-insert : " + lec.getName());
                    } else {
                        map.remove(lec.getId());
                    }

                    // update
                    if(lec_ != null) {
                        if(lec.getVersion() != lec_.getVersion()) {
                            wordCheckFlag = true;
                        }

                        if (!lec.equals(lec_)) {
                            dbHelper.updateLectureWithOutVersion(lec);
                            Log.d(TAG, "[test]-checkLec-update : " + lec.getName());
                        }
                    }

                    if(wordCheckFlag) {
                        checkWord(lecDs, lec);
                    }
                }

                for(String key: map.keySet()) {
                    dbHelper.deleteLecture(key);
                    Log.d(TAG, "[test]-checkLec-remove : " + map.get(key).getName());
                }

                hideProgressDialog();
            }
        });
    }

    private void checkWord(DocumentSnapshot lecDs, final Lecture lec__) {
        Log.d(TAG, "[test]-checkWord LEC : " + lec__.getName());
    }

    private void checkLev() {
        mDb.collection("levels").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot levsDs) {

                Map<Integer, Level> map = new HashMap<>();

                for(Level lev: dbHelper.getLevels()) {
                    map.put(lev.getId(), lev);
                }

                for(DocumentSnapshot levDs: levsDs) {
                    Level lev = levDs.toObject(Level.class);
                    lev.setId(Integer.parseInt(levDs.getId()));
                    Level lev_ = map.get(lev.getId());

                    if (lev_ == null) {
                        dbHelper.insertLevel(lev);
                    } else {
                        map.remove(lev.getId());
                    }

                    if(lev_ != null && !lev.equals(lev_)) {
                        dbHelper.updateLevel(lev);
                    }
                }

                for(int key: map.keySet()) {
                    dbHelper.deleteLevel(key);
                }
            }
        });
    }
}
