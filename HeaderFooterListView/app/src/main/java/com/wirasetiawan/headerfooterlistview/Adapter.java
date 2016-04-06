package com.wirasetiawan.headerfooterlistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by wira on 4/7/16.
 */
public class Adapter extends BaseAdapter {

    List<Model> models;
    Context context;

    public Adapter(Context context, List<Model> models){
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        int couter = 0;
        for (int i = 0; i < models.size(); i++) {
            Model model = models.get(i);
            couter += 1 + 1 + model.contents.size();
        }

        return couter;
    }

    @Override
    public Object getItem(int position) {
        for (int i = 0; i < models.size(); i++) {
            Model model = models.get(i);
            int childSize = model.contents.size();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
