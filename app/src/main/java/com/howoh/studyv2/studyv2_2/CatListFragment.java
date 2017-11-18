package com.howoh.studyv2.studyv2_2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.vo.Subject;

public class CatListFragment extends Fragment {

    private static final String TAG = CatListFragment.class.getSimpleName();

    DBHelper dbHelper;
    private Subject sub;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cat_list, container, false);
        dbHelper = DBHelper.getInstance(getActivity());

        sub = dbHelper.getSubject(getArguments().getString("subId"));

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(sub.getName());

        return v;
    }
}
