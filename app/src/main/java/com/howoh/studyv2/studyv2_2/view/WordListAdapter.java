package com.howoh.studyv2.studyv2_2.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.howoh.studyv2.studyv2_2.R;
import com.howoh.studyv2.studyv2_2.WordListFragment;
import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.vo.WordListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-18.
 */

public class WordListAdapter extends BaseAdapter
        implements View.OnClickListener {

    private static final String TAG = WordListFragment.class.getSimpleName();

    private DBHelper dbHelper;
    private Context context;
    private List<WordListView> list = new ArrayList<>();

    boolean headFlag;   // true: head1,2 - O, false: head1 - O, head2 - X
    boolean bodyFlag;   // true: head1,2 - O, false: head1,2 - X

    public WordListAdapter(Context context, List<WordListView> list, boolean headFlag, boolean bodyFlag) {
        dbHelper = DBHelper.getInstance(context);
        this.context = context;
        this.list = list;
        this.headFlag = headFlag;
        this.bodyFlag = bodyFlag;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_word, parent, false);
        }

        ImageView levelImageView = (ImageView) convertView.findViewById(R.id.listview_word_level);
        LinearLayout headLL = (LinearLayout) convertView.findViewById(R.id.listview_word_headll);
        LinearLayout bodyLL = (LinearLayout) convertView.findViewById(R.id.listview_word_bodyll);
        TextView head1TextView = (TextView) convertView.findViewById(R.id.listview_word_head1);
        TextView head2TextView = (TextView) convertView.findViewById(R.id.listview_word_head2);
        TextView body1TextView = (TextView) convertView.findViewById(R.id.listview_word_body1);
        TextView body2TextView = (TextView) convertView.findViewById(R.id.listview_word_body2);
        TextView etcTextView = (TextView) convertView.findViewById(R.id.listview_word_etc);
        ImageView upBtn = (ImageView) convertView.findViewById(R.id.listview_word_up);
        ImageView downBtn = (ImageView) convertView.findViewById(R.id.listview_word_down);

        final WordListView wordListView = list.get(position);
        wordListView.setHeadFlag(headFlag);
        wordListView.setBodyFlag(bodyFlag);

        head1TextView.setText(wordListView.getHead1());
        head2TextView.setText(wordListView.getHead2());
        if(bodyFlag) body1TextView.setText(wordListView.getBody1());
        if(bodyFlag) body2TextView.setText(wordListView.getBody2());
        etcTextView.setText(wordListView.getCategoryName() + " - " + wordListView.getLectureName());

        levelImageView.setBackgroundColor(getLevelColor(wordListView.getLevelId()));
        headLL.setOnClickListener(this);
        bodyLL.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);

        levelImageView.setTag(pos);
        headLL.setTag(pos);
        bodyLL.setTag(pos);
        upBtn.setTag(pos);
        downBtn.setTag(pos);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.listview_word_headll:
                clickHead(v, pos);
                break;
            case R.id.listview_word_bodyll:
                clickBody(v, pos);
                break;
            case R.id.listview_word_up:
                updateWordLevel(v, pos, 1);
                break;
            case R.id.listview_word_down:
                updateWordLevel(v, pos, -1);
                break;
        }
    }

    private void clickHead(View v, int pos) {
        WordListView wordListView = list.get(pos);
        wordListView.setHeadFlag(!wordListView.isHeadFlag());

        TextView head1TextView = (TextView) v.findViewById(R.id.listview_word_head1);
        TextView head2TextView = (TextView) v.findViewById(R.id.listview_word_head2);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) head1TextView.getLayoutParams();
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) head2TextView.getLayoutParams();

        if(wordListView.isHeadFlag()) {
            params1.height = context.getResources().getDimensionPixelSize(R.dimen.wordlist_head1_height);
            params2.height = context.getResources().getDimensionPixelSize(R.dimen.wordlist_head2_height);
            head1TextView.setGravity(Gravity.BOTTOM);
        } else {
            params1.height = context.getResources().getDimensionPixelSize(R.dimen.wordlist_head_height);
            params2.height = context.getResources().getDimensionPixelSize(R.dimen.height_0);
            head1TextView.setGravity(Gravity.CENTER_VERTICAL);
        }

        head1TextView.setLayoutParams(params1);
        head2TextView.setLayoutParams(params2);
    }

    private void clickBody(View v, int pos) {
        WordListView wordListView = list.get(pos);
        wordListView.setBodyFlag(!wordListView.isBodyFlag());

        TextView body1TextView = (TextView) v.findViewById(R.id.listview_word_body1);
        TextView body2TextView = (TextView) v.findViewById(R.id.listview_word_body2);

        if(wordListView.isBodyFlag()) {
            body1TextView.setText(wordListView.getBody1());
            body2TextView.setText(wordListView.getBody2());
        } else {
            body1TextView.setText(null);
            body2TextView.setText(null);
        }
    }


    private void updateWordLevel(View ve, int position, int levIncrement) {
        WordListView wordListView = list.get(position);
        LinearLayout v = (LinearLayout) ve.getParent().getParent();

        int level = 0;

        if(wordListView == null) {
            return;
        }
        level = wordListView.getLevelId() + levIncrement;

        if(level > 2 || level < -2) {
            return;
        }

        dbHelper.updateWordLevel(wordListView.getId(), level);
        wordListView.setLevelId(level);
        ImageView levelImageView = (ImageView) v.findViewById(R.id.listview_word_level);
        levelImageView.setBackgroundColor(getLevelColor(level));
    }

    public int getLevelColor(int level) {
        switch (level) {
            case 2:
                return context.getResources().getColor(R.color.level2);
            case 1:
                return context.getResources().getColor(R.color.level1);
            case 0:
                return context.getResources().getColor(R.color.level0);
            case -1:
                return context.getResources().getColor(R.color.level_1);
            case -2:
                return context.getResources().getColor(R.color.level_2);
            default:
                return context.getResources().getColor(R.color.level0);
        }
    }

}
