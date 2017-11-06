package com.howoh.studyv2.studyv2_2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.vo.Subject;

public class TestFragment extends Fragment implements View.OnClickListener {

    private static String TAG = TestFragment.class.getSimpleName();

    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test, container, false);

        dbHelper = DBHelper.getInstance(getActivity());

        Button createBtn = (Button) v.findViewById(R.id.btn_create);
        Button dropBtn = (Button) v.findViewById(R.id.btn_drop);
        Button selectBtn = (Button) v.findViewById(R.id.btn_select);
        createBtn.setOnClickListener(this);
        dropBtn.setOnClickListener(this);
        selectBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase db = dbHelper.getReadableDatabase() ;

        switch (v.getId()) {
            case R.id.btn_create:
                dbHelper.onCreate(db);
                break;
            case R.id.btn_drop:
                dbHelper.dropTable(db);
                break;
            case R.id.btn_select:
                for(Subject subject : dbHelper.getSubjecs()) {
                    Log.d(TAG, "[test]-btn_select" + subject.toString());
                }
                break;
        }
    }
}
