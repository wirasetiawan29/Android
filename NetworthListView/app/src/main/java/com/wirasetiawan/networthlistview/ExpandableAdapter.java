package com.wirasetiawan.networthlistview;

/**
 * Created by wira on 3/28/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


/**
 * Created by wira on 1/20/16.
 */
public class ExpandableAdapter extends BaseAdapter {

    List<Item> items;
    Context context;

    public ExpandableAdapter(Context context, List<Item> items){
        this.items = items;
        this.context = context;
    }


    @Override
    public int getCount(){
        int counter = 0;
        for (int i = 0; i < items.size(); i++) {
            //assume
            Item item = items.get(i);
            counter += 1 + 1 + item.childs.size();
        }
        return counter;
//        return items.size();
    }

    @Override
    public Object getItem(int position){
        for (int i = 0; i < items.size(); i++) {
            //assume
            Item item = items.get(i);
            int childSize = item.childs.size();
            int maxVal = 1 + 1 + childSize; //Header cell + Footer cell + child Size cell

            if (position == 0) {
                return item;//Header Object
            } else if (position == (maxVal - 1)) {
                return item;//Footer Object
            } else if (position < (maxVal - 1)) {
                return item.childs.get(position -1);
            }

            position -= maxVal;
        }

        return items.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        for (int i = 0; i < items.size(); i++) {
            //assume
            Item item = items.get(i);
            int childSize = item.childs.size();
            int maxVal = 1 + 1 + childSize; //Header cell + Footer cell + child Size cell

            if (position == 0) {
                return -1;
            } else if (position == (maxVal - 1)) {
                return -2;
            } else if (position < (maxVal - 1)) {
                return -3;
            }

            position -= maxVal;
        }
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        for (int i = 0; i < items.size(); i++) {
            //assume
            Item item = items.get(i);
            int childSize = item.childs.size();
            int maxVal = 1 + 1 + childSize; //Header cell + Footer cell + child Size cell

            if (position == 0) {
                convertView = LayoutInflater.from(context).inflate(R.layout.header_item, parent, false);
                break;
            } else if (position == (maxVal - 1)) {
                convertView = LayoutInflater.from(context).inflate(R.layout.footer_item, parent, false);
                break;
            } else if (position < (maxVal - 1)) {
                SubItem model = (SubItem)getItem(position);
                convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
                TextView descriptionTextView = (TextView)convertView.findViewById(R.id.description_text_view);
                TextView amountTextView = (TextView)convertView.findViewById(R.id.amount_text_view);
                descriptionTextView.setText(model.getTitle());
                amountTextView.setText(model.getAmount());
                break;
            }

            position -= maxVal;
        }

        return convertView;
    }
}

