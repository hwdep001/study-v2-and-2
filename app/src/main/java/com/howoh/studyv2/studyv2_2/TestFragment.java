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
import com.howoh.studyv2.studyv2_2.service.ContactDBWord;
import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.vo.Category;
import com.howoh.studyv2.studyv2_2.vo.Lecture;
import com.howoh.studyv2.studyv2_2.vo.Level;
import com.howoh.studyv2.studyv2_2.vo.Subject;
import com.howoh.studyv2.studyv2_2.vo.Word;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestFragment extends BaseFragment implements View.OnClickListener {

    private static String TAG = TestFragment.class.getSimpleName();

    private FirebaseFirestore mDb;
    DBHelper dbHelper;

    private boolean isLecCheck = false;
    private boolean isWordCheck = false;

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
        Button wordSelectBtn = (Button) v.findViewById(R.id.btn_word_select);
        Button wordIfBtn = (Button) v.findViewById(R.id.btn_word_if);

        allDelBtn.setOnClickListener(this);
        verCheckBtn.setOnClickListener(this);

        subSelectBtn.setOnClickListener(this);
        catSelectBtn.setOnClickListener(this);
        lecSelectBtn.setOnClickListener(this);
        levSelectBtn.setOnClickListener(this);
        wordSelectBtn.setOnClickListener(this);
        wordIfBtn.setOnClickListener(this);

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
                if(dbHelper.isExistTable(ContactDBSubject.TABLE_NAME)) {
                    for(Subject subject : dbHelper.getSubjecs()) {
                        Log.d(TAG, "[test]-select subs" + subject.toString());
                    }
                }
                break;
            case R.id.btn_cat_select:
                if(dbHelper.isExistTable(ContactDBCategory.TABLE_NAME)) {
                    for(Category category: dbHelper.getCategories()) {
                        Log.d(TAG, "[test]-select cats: " + category.toString());
                    }
                }
                break;
            case R.id.btn_lec_select:
                if(dbHelper.isExistTable(ContactDBLecture.TABLE_NAME)) {
                    for(Lecture lecture: dbHelper.getLectures()) {
                        Log.d(TAG, "[test]-select lecs: " + lecture.toString());
                    }
                }
                break;
            case R.id.btn_lev_select:
                if(dbHelper.isExistTable(ContactDBLevel.TABLE_NAME)) {
                    for(Level level: dbHelper.getLevels()) {
                        Log.d(TAG, "[test]-select levs: " + level.toString());
                    }
                }
                break;
            case R.id.btn_word_select:
                if(dbHelper.isExistTable(ContactDBWord.TABLE_NAME)) {
                    for(Word word: dbHelper.getWords()) {
                        Log.d(TAG, "[test]-select words: " + word.toString());
                    }
                }
                break;
            case R.id.btn_word_if:
                Log.d(TAG, "[test]-if table word: " + dbHelper.isExistTable(ContactDBWord.TABLE_NAME));
                break;
        }
    }

    private void checkVersion(final SQLiteDatabase db) {
        isLecCheck = false;
        isWordCheck = false;

        showProgressDialog(null);
        dbHelper.onCreate(db);
        checkLev();
        checkSub();
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
                            isLecCheck = true;
                        }
                    }

                    for(String key: map.keySet()) {
                        dbHelper.deleteCategory(key);
                        Log.d(TAG, "[test]-checkCat-remove : " + map.get(key).getName());
                    }
                }
            });
        }

        if(!isLecCheck) {
            hideProgressDialog();
        }
    }

    private void checkLec(DocumentSnapshot catDs, final Category cat) {

        Log.d(TAG, "[test]-checkLec CAT: " + cat.getName());

        catDs.getReference().collection("lecs").orderBy("num").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot lecsDs) {

                Map<String, Lecture> map = new HashMap<>();

                for(Lecture lec: dbHelper.getLectures(cat.getId())) {
                    map.put(lec.getId(), lec);
                }

                for(DocumentSnapshot lecDs: lecsDs) {

                    boolean wordCheckFlag = false;

                    Lecture lec = lecDs.toObject(Lecture.class);
                    lec.setId(lecDs.getId());
                    lec.setCategoryId(cat.getId());
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
                        checkWord(lecDs, cat, lec);
                        isWordCheck = true;
                    }
                }

                for(String key: map.keySet()) {
                    dbHelper.deleteLecture(key);
                    Log.d(TAG, "[test]-checkLec-remove : " + map.get(key).getName());
                }
            }
        });

        if(!isWordCheck) {
            hideProgressDialog();
            dbHelper.updateCategory(cat);
        }
    }

    private void checkWord(final DocumentSnapshot lecDs, final Category cat, final Lecture lec) {

        Log.d(TAG, "[test]-checkWord LEC : " + lec.getName());

        lecDs.getReference().collection("words").orderBy("num").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot wordsDs) {

                Map<String, Word> map = new HashMap<>();

                for(Word word: dbHelper.getWords(lec.getId())) {
                    map.put(word.getId(), word);
                }

                for(DocumentSnapshot wordDs: wordsDs) {
                    Word word = wordDs.toObject(Word.class);
                    word.setId(wordDs.getId());
                    word.setLectureId(lec.getId());
                    Word word_ = map.get(word.getId());

                    // insert
                    if (word_ == null) {
                        dbHelper.insertWord(word);
                        Log.d(TAG, "[test]-checkWord-insert : " + word.getHead1());
                    } else {
                        map.remove(word.getId());
                    }

                    // update
                    if(word_ != null && !word.equals(word_)) {
                        dbHelper.updateWithOutLevel(word);
                        Log.d(TAG, "[test]-checkWord-update : " + word.getHead1());
                    }
                }

                for(String key: map.keySet()) {
                    dbHelper.deleteWord(key);
                    Log.d(TAG, "[test]-checkWord-remove : " + map.get(key).getHead1());
                }

                // lecture, cat version update
                dbHelper.updateLecture(lec);
                dbHelper.updateCategory(cat);

                hideProgressDialog();
            }
        });
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



    /*
    * word 체크까지 함
    * processDialog 문제 해결해야 함
    * word 테스트 더 해보기 lec 늘려서
    * */
}
