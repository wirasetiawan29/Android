package com.wirasetiawan.dialogfragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerArrowIndicator viewPagerArrowIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerArrowIndicator = (ViewPagerArrowIndicator) findViewById(R.id.viewPagerArrowIndicator);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        viewPagerArrowIndicator.bind(viewPager);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                FragmentManager fm = getSupportFragmentManager();
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });


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

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos) {
            switch(pos) {
                case 0:
                    return Fragment.newInstance("Halaman 1");
                case 1:
                    return Fragment.newInstance("Halaman 2");
                case 2:
                    return Fragment.newInstance("Halaman 3");
                case 3:
                    return Fragment.newInstance("Halaman 4");
                default:
                    return Fragment.newInstance("Halaman 5");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
