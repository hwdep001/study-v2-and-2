package com.howoh.studyv2.studyv2_2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.view.IdNameListAdapter;
import com.howoh.studyv2.studyv2_2.vo.IdNameListView;
import com.howoh.studyv2.studyv2_2.vo.Lecture;

import java.util.List;

public class WordListActivity extends BaseActivity implements
        AdapterView.OnItemClickListener {

    private static final String TAG = WordListActivity.class.getSimpleName();

    private FragmentManager fm;

    private String subId;
    private String catId;
    private String lecId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_wordlist, getWordListFragment()).commit();
    }

    private WordListFragment getWordListFragment() {
        WordListFragment wordListFragment = new WordListFragment();
        Bundle bundle = new Bundle();

        bundle.putString("subId", getIntent().getStringExtra("subId"));
        bundle.putString("catId", getIntent().getStringExtra("catId"));
        bundle.putString("lecId", getIntent().getStringExtra("lecId"));
        wordListFragment.setArguments(bundle);

        return wordListFragment;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_word_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_word_list) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
