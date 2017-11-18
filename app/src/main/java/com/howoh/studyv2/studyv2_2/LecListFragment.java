package com.howoh.studyv2.studyv2_2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.view.IdNameListAdapter;
import com.howoh.studyv2.studyv2_2.vo.IdNameListView;
import com.howoh.studyv2.studyv2_2.vo.Category;

import java.util.List;

public class LecListFragment extends BaseFragment {

    private static final String TAG = LecListFragment.class.getSimpleName();

    private FragmentManager fm;
    private DBHelper dbHelper;
    private View v;
    private String catId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_idname_list, container, false);
        fm = getActivity().getSupportFragmentManager();
        dbHelper = DBHelper.getInstance(getActivity());

        Category cat = null;

        catId = getArguments().getString("catId");
        cat = dbHelper.getCategory(catId);

        if(cat != null) {
            setTitle(cat.getName());
            setArrayAdapter(dbHelper.getLecturesForView(cat.getId()));
        }

        return v;
    }

    private void setArrayAdapter(List<IdNameListView> items) {

        IdNameListAdapter adapter = new IdNameListAdapter(items);
        ListView listView = (ListView) v.findViewById(R.id.frg_idname_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IdNameListView item = (IdNameListView) parent.getItemAtPosition(position);
                moveWordListFragment(((IdNameListView) parent.getItemAtPosition(position)).getId());
            }
        });
    }

    private void moveWordListFragment(String lecId) {
//        Bundle bundle = new Bundle();
//        bundle.putString("lecId", lecId);
//
//        LecListFragment lecListFragment = new LecListFragment();
//        lecListFragment.setArguments(bundle);
//
//        fm.beginTransaction().replace(R.id.content_main, lecListFragment).addToBackStack(null).commit();
    }
}
