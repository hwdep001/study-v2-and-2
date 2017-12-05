package com.howoh.studyv2.studyv2_2.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.howoh.studyv2.studyv2_2.R;
import com.howoh.studyv2.studyv2_2.vo.WordListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-18.
 */

public class WordListAdapter extends BaseAdapter implements View.OnClickListener {

    public interface ListBtnClickListener {
        void onListBtnClick(int viewId) ;
    }
    private ListBtnClickListener listBtnClickListener ;


    private List<WordListView> list = new ArrayList<>();

    public WordListAdapter(List<WordListView> list, ListBtnClickListener clickListener) {
        this.list = list;
        this.listBtnClickListener = clickListener;
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

        TextView head1TextView = (TextView) convertView.findViewById(R.id.listview_word_head1);
        TextView head2TextView = (TextView) convertView.findViewById(R.id.listview_word_head2);
        TextView body1TextView = (TextView) convertView.findViewById(R.id.listview_word_body1);
        TextView body2TextView = (TextView) convertView.findViewById(R.id.listview_word_body2);
        TextView etcTextView = (TextView) convertView.findViewById(R.id.listview_word_etc);

        ImageButton upBtn = (ImageButton) convertView.findViewById(R.id.listview_word_up);
        ImageButton downBtn = (ImageButton) convertView.findViewById(R.id.listview_word_down);

        final WordListView wordListView = list.get(position);
        head1TextView.setText(wordListView.getHead1());
        head2TextView.setText(wordListView.getHead2());
        body1TextView.setText(wordListView.getBody1());
        body2TextView.setText(wordListView.getBody2());
        etcTextView.setText(wordListView.getCategoryName() + " - " + wordListView.getLectureName());

        head1TextView.setOnClickListener(this);
        head2TextView.setOnClickListener(this);
        body1TextView.setOnClickListener(this);
        body2TextView.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);

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
        if(this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick(v.getId());
        }
    }
}
