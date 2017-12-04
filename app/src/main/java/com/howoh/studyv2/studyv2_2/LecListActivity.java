package com.howoh.studyv2.studyv2_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.view.IdNameListAdapter;
import com.howoh.studyv2.studyv2_2.vo.Category;
import com.howoh.studyv2.studyv2_2.vo.IdNameListView;

import java.util.List;

public class LecListActivity extends BaseActivity implements
        AdapterView.OnItemClickListener {

    private static final String TAG = LecListActivity.class.getSimpleName();

    private DBHelper dbHelper;
    private String subId;
    private String catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leclist);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = DBHelper.getInstance(this);
        subId = getIntent().getStringExtra("subId");
        catId = getIntent().getStringExtra("catId");

        Category cat = dbHelper.getCategory(catId);

        if(cat != null) {
            setTitle(cat.getName());
            setArrayAdapter(dbHelper.getLecturesForView(cat.getId()));
        }
    }

    private void setArrayAdapter(List<IdNameListView> items) {
        IdNameListAdapter adapter = new IdNameListAdapter(items);

        ListView listView = (ListView) findViewById(R.id.frg_idname_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String lecId = ((IdNameListView) parent.getItemAtPosition(position)).getId();

//        Intent intent = new Intent(this, .class);
//        intent.putExtra("subId", subId);
//        intent.putExtra("catId", catId);
//        intent.putExtra("lecId", lecId);
//        startActivity(intent);
    }
}
