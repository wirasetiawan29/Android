package com.logbook.wira.flipviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.logbook.wira.flipviewpager.R;
import com.logbook.wira.flipviewpager.Utils;
import com.logbook.wira.flipviewpager.model.Makanan;
import com.yalantis.flipviewpager.adapter.BaseFlipAdapter;
import com.yalantis.flipviewpager.utils.FlipSettings;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView list = (ListView) findViewById(R.id.makanan);
        //final ListView list_makanan = (ListView) findViewById(R.id.makanan);


        FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
        list.setAdapter(new MakananAdapter(this, Utils.makanan, settings));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Makanan f = (Makanan) list.getAdapter().getItem(position);

                Toast.makeText(MainActivity.this, f.getName(), Toast.LENGTH_SHORT).show();
            }
        });


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    class MakananAdapter extends BaseFlipAdapter<Makanan>{
        private final int PAGES = 3;
        private int[] IDS_INTEREST = {R.id.interest_1, R.id.interest_2, R.id.interest_3, R.id.interest_4, R.id.interest_5};

        public MakananAdapter(Context context, List<Makanan> items, FlipSettings settings){
            super(context, items, settings);
        }

        @Override
        public View getPage(int position, View convertView, ViewGroup parent, Makanan makanan1, Makanan makanan2){
            final MakananHolder holder;

            if (convertView == null) {
                holder = new MakananHolder();
                convertView = getLayoutInflater().inflate(R.layout.makanan_merge_page, parent, false);
                holder.leftIcon = (ImageView) convertView.findViewById(R.id.first);
                holder.rightIcon = (ImageView) convertView.findViewById(R.id.second);
                holder.infoPage = getLayoutInflater().inflate(R.layout.makanan_info, parent, false);
                holder.nama = (TextView) holder.infoPage.findViewById(R.id.nama);

                for (int id : IDS_INTEREST)
                    holder.interests.add((TextView) holder.infoPage.findViewById(id));

                convertView.setTag(holder);
            } else {
                holder = (MakananHolder) convertView.getTag();
            }

            switch (position){
                case 1:
                    holder.leftIcon.setImageResource(makanan1.getIcon());
                    if (makanan2 != null)
                        holder.rightIcon.setImageResource(makanan2.getIcon());
                    break;
                default:
                    fillHolder(holder, position == 0 ? makanan1 : makanan2);
                    holder.infoPage.setTag(holder);
                    return holder.infoPage;
            }
            return convertView;
        }


        @Override
        public int getPagesCount(){return PAGES;}
        private void fillHolder(MakananHolder holder, Makanan makanan){
            if (makanan == null)
                return;;
            Iterator<TextView> iViews = holder.interests.iterator();
            Iterator<String> iInterests = makanan.getInterests().iterator();
            while (iViews.hasNext() && iInterests.hasNext())
                iViews.next().setText(iInterests.next());
            holder.infoPage.setBackgroundColor(getResources().getColor(makanan.getBackground()));
            holder.nama.setText(makanan.getName());
        }

        class MakananHolder {
            ImageView leftIcon;
            ImageView rightIcon;
            View infoPage;

            List<TextView> interests = new ArrayList<>();
            TextView nama;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
