package com.wirasetiawan.expandlistview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;
import java.util.Objects;

/**
 * Created by wira on 1/19/16.
 */
public class ExpandableAdapter extends BaseAdapter {

    List<Item> items;
    Context context;

    public class Row{
        AppCompatTextView Judul;
        AppCompatTextView Deskripsi;
        FrameLayout Wrapper;
        ImageView Arrow;
    }

    public ExpandableAdapter(Context context, List<Item> items){
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Row row;

        if(convertView == null){
            row = new Row();
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
            row.Wrapper = (FrameLayout) convertView.findViewById(R.id.wrapper);
            row.Judul = (AppCompatTextView) convertView.findViewById(R.id.judul);
            row.Deskripsi = (AppCompatTextView) convertView.findViewById(R.id.deskripsi);
            row.Arrow = (ImageView) convertView.findViewById(R.id.arrow);

            convertView.setTag(row);
        } else {
            row = (Row) convertView.getTag();
        }

        //Memperbaharui tampilan
        Item item = items.get(position);
        if(item.isExpanded){
            row.Wrapper.setVisibility(View.VISIBLE);
            row.Arrow.setRotation(180f);
        }else {
            row.Wrapper.setVisibility(View.GONE);
            row.Arrow.setRotation(0f);
        }

        row.Judul.setText(item.title);
        row.Deskripsi.setText(item.desciption);

        return convertView;
    }
}
