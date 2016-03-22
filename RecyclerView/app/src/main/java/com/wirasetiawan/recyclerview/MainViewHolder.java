package com.wirasetiawan.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wira on 3/21/16.
 */
public class MainViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public MainViewHolder(View itemView) {
        super(itemView);

        textView = (TextView)itemView.findViewById(R.id.adapter_main_text_view);
    }

    public TextView getTextView() {
        return textView;
    }
}
