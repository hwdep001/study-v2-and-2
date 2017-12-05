package com.howoh.studyv2.studyv2_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.howoh.studyv2.studyv2_2.service.ContactDBSubject;
import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.view.IdNameListAdapter;
import com.howoh.studyv2.studyv2_2.view.WordListAdapter;
import com.howoh.studyv2.studyv2_2.vo.IdNameListView;
import com.howoh.studyv2.studyv2_2.vo.Lecture;
import com.howoh.studyv2.studyv2_2.vo.Subject;
import com.howoh.studyv2.studyv2_2.vo.Word;
import com.howoh.studyv2.studyv2_2.vo.WordListView;

import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends BaseFragment implements WordListAdapter.ListBtnClickListener {

    private static final String TAG = WordListFragment.class.getSimpleName();

    private DBHelper dbHelper;
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
        WordListAdapter adapter = new WordListAdapter(items, this);

        ListView listView = (ListView) v.findViewById(R.id.frg_word_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"[test]----------------------------------------------");
            }
        });
    }

    @Override
    public void onListBtnClick(int viewId) {
        switch (viewId) {
            case R.id.listview_word_head1:
                Log.d(TAG,"[test] listview_word_head1");
                break;
            case R.id.listview_word_head2:
                Log.d(TAG,"[test] listview_word_head2");
                break;
            case R.id.listview_word_body1:
                Log.d(TAG,"[test] listview_word_body1");
                break;
            case R.id.listview_word_body2:
                Log.d(TAG,"[test] listview_word_body2");
                break;
            case R.id.listview_word_up:
                Log.d(TAG,"[test] listview_word_up");
                break;
            case R.id.listview_word_down:
                Log.d(TAG,"[test] listview_word_down");
                break;
        }
    }
}
