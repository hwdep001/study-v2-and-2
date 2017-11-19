package com.howoh.studyv2.studyv2_2.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.howoh.studyv2.studyv2_2.R;
import com.howoh.studyv2.studyv2_2.vo.IdNameListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howoh on 2017-11-18.
 */

public class IdNameListAdapter extends BaseAdapter {

    private List<IdNameListView> list = new ArrayList<>();

    public IdNameListAdapter(List<IdNameListView> list) {
        this.list = list;
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
            convertView = inflater.inflate(R.layout.listview_idname, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.listview_name) ;
        IdNameListView catListView = list.get(position);
        nameTextView.setText(catListView.getName());

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
}
