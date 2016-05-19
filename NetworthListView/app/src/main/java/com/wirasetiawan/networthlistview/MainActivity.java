package com.wirasetiawan.networthlistview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Item> items = new ArrayList<>();
    private ListView listViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewItems = (ListView) findViewById(R.id.networth_before);
        ExpandableAdapter adapters = getAdapter();

        listViewItems.setAdapter(adapters);
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

    private ExpandableAdapter getAdapter(){

        for(int i = 0; i < 5; i++){
            Item item = new Item();
            item.childs.add(new SubItem("ABC","10000"));
            item.childs.add(new SubItem("DEF","12000"));
            item.childs.add(new SubItem("FHI","13000"));
            items.add(item);
        }

        return new ExpandableAdapter(this, items);
    }
}
