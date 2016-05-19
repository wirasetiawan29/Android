package com.wirasetiawan.networthlistview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wira on 3/28/16.
 */
public class Item{

    public List<SubItem> childs;

    public Item(){
        childs = new ArrayList<>();
    }

    public Item(Parcel in){
        childs = new ArrayList<>();
    }
}

