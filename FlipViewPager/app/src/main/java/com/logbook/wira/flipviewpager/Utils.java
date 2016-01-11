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
        makanan.add(new Makanan(R.drawable.soto, "SOTO", R.color.sienna, "sup ayam", "soun", "telur ayam", "kol", "kentang"));
        makanan.add(new Makanan(R.drawable.nasi_goreng, "NASI GORENG", R.color.saffron, "nasi", "telur", "bawang merah", "cabai rawit", "garam dan penyedap rasa"));
        makanan.add(new Makanan(R.drawable.rendang, "RENDANG", R.color.green, "kelapa parut", "daging sapi", "air untuk santan", "cabai merah", "lengkuas"));
        makanan.add(new Makanan(R.drawable.sate, "SATE", R.color.pink, "daging sapi", "asam", "gula merah", "bawang putih", "garam"));
        makanan.add(new Makanan(R.drawable.dendeng, "DENDENG", R.color.orange, "daging sapi", "jahe", "bawang putih", "cabe merah", "bawang merah"));
        makanan.add(new Makanan(R.drawable.sup, "SUP", R.color.saffron, "macaroni", "bawang bombay", "Tomat segar", "daging ayam", "Wortel"));
        makanan.add(new Makanan(R.drawable.soto, "MIE GORENG", R.color.green, "Cinema", "Music", "Tatoo", "Animals", "Management"));
        makanan.add(new Makanan(R.drawable.soto, "AYAM SINGGANG", R.color.purple, "Android", "IOS", "Application", "Development", "Company"));
    }
}