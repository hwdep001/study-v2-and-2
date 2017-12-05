package com.howoh.studyv2.studyv2_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.view.WordListAdapter;
import com.howoh.studyv2.studyv2_2.vo.IdNameListView;
import com.howoh.studyv2.studyv2_2.vo.Lecture;
import com.howoh.studyv2.studyv2_2.vo.WordListView;

import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends BaseFragment {

    private static final String TAG = WordListFragment.class.getSimpleName();

    private DBHelper dbHelper;
    WordListAdapter adapter;
    private View v;
    private String subId;
    private String catId;
    private String lecId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_word_list, container, false);
        dbHelper = DBHelper.getInstance(getActivity());

        subId = getArguments().getString("subId");
        catId = getArguments().getString("catId");
        lecId = getArguments().getString("lecId");

        Lecture lec = dbHelper.getLecture(lecId);

        if(lec != null) {
            List<IdNameListView> items = new ArrayList<>();
            for(int i=1; i<=20; i++) {
                IdNameListView newItem = new IdNameListView();
                newItem.setId("id" + i);
                newItem.setName("name" + i);
                items.add(newItem);
            }

            setTitle(lec.getName());
            setArrayAdapter(dbHelper.getWordsForView(lec.getId()));
        }

        return v;
    }

    private void setArrayAdapter(List<WordListView> items) {
        adapter = new WordListAdapter(getActivity(), items, false, false);

        ListView listView = (ListView) v.findViewById(R.id.frg_word_listview);
        listView.setAdapter(adapter);
    }
}
