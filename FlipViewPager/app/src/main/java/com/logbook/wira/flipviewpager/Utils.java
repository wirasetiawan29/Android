package com.logbook.wira.flipviewpager;

import com.logbook.wira.flipviewpager.model.Makanan;

import java.util.ArrayList;
import java.util.List;
import com.logbook.wira.flipviewpager.R;

/**
 * Created by wira on 1/10/16.
 */
public class Utils {
    public static final List<Makanan> makanan = new ArrayList<>();

    static {
        makanan.add(new Makanan(R.drawable.soto, "SOTO", R.color.sienna, "Sport", "Literature", "Music", "Art", "Technology"));
        makanan.add(new Makanan(R.drawable.soto, "NASI GORENG", R.color.saffron, "Travelling", "Flights", "Books", "Painting", "Design"));
        makanan.add(new Makanan(R.drawable.soto, "RENDANG", R.color.green, "Sales", "Pets", "Skiing", "Hairstyles", "Сoffee"));
        makanan.add(new Makanan(R.drawable.soto, "SATE", R.color.pink, "Android", "Development", "Design", "Wearables", "Pets"));
        makanan.add(new Makanan(R.drawable.soto, "DENDENG", R.color.orange, "Design", "Fitness", "Healthcare", "UI/UX", "Chatting"));
        makanan.add(new Makanan(R.drawable.soto, "SUP", R.color.saffron, "Development", "Android", "Healthcare", "Sport", "Rock Music"));
        makanan.add(new Makanan(R.drawable.soto, "MIE GORENG", R.color.green, "Cinema", "Music", "Tatoo", "Animals", "Management"));
        makanan.add(new Makanan(R.drawable.soto, "AYAM SINGGANG", R.color.purple, "Android", "IOS", "Application", "Development", "Company"));
    }
}