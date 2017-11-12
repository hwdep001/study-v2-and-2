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
import com.howoh.studyv2.studyv2_2.service.ContactDBSubject;
import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.vo.Category;
import com.howoh.studyv2.studyv2_2.vo.Lecture;
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

        allDelBtn.setOnClickListener(this);
        verCheckBtn.setOnClickListener(this);

        subSelectBtn.setOnClickListener(this);
        catSelectBtn.setOnClickListener(this);
        lecSelectBtn.setOnClickListener(this);

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
        }
    }

    private void checkVersion(final SQLiteDatabase db) {
        showProgressDialog(null);
        dbHelper.onCreate(db);
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
                        Category cat = catDs.toObject(Category.class);
                        cat.setId(catDs.getId());
                        cat.setSubjectId(subDs.getId());
                        Category cat_ = map.get(cat.getId());
                        Log.d(TAG, "[test]- cat_: " + (cat_== null));

                        if (cat_ == null) {
                            dbHelper.insertCategory(cat);
                        } else {
                            map.remove(cat.getId());
                        }

                        if (!cat.equals(cat_)) {
                            dbHelper.updateCategory(cat);
                        }
                    }

                    for(String key: map.keySet()) {
                        dbHelper.deleteCategory(key);
                    }

                    hideProgressDialog();
                }
            });
        }
    }
}
