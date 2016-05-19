package com.jojonomic.janus.expandlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wira on 4/29/16.
 */
public class ExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<ExpandModel> expandModelArrayList;

    public ExpandAdapter(Context context, List<ExpandModel> expandModelArrayList){
        this.context = context;
        this.expandModelArrayList = expandModelArrayList;
//        educationPlanModelList = new ArrayList<>();
    }



    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ExpandModelItem> expandModelItemArrayList = expandModelArrayList.get(groupPosition).getProductList();
        return expandModelItemArrayList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        ExpandModelItem expandModelItem = (ExpandModelItem) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.adapter_item, null);
        }


        TextView childItem = (TextView) view.findViewById(R.id.adapter_education_level_item_text_view);
        childItem.setText(expandModelItem.getName().trim());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<ExpandModelItem> expandModelItemArrayList = expandModelArrayList.get(groupPosition).getProductList();
        return expandModelArrayList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return expandModelArrayList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return expandModelArrayList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

        ExpandModel expandModel = (ExpandModel) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.adapter_heading, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(expandModel.getName().trim());


        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
