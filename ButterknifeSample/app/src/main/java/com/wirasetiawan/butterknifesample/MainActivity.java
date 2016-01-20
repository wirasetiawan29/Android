package com.wirasetiawan.butterknifesample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind({R.id.button1,R.id.button2,R.id.button3})
    List<Button> buttons;

    //the Butter Knife Action
    final ButterKnife.Action<Button> CHANGE_COLOR = new ButterKnife.Action<Button>() {

        @Override
        public void apply(Button button, int index) {

            setTitle("Code with Butter Knife");
            buttons.get(0).setBackgroundColor(Color.WHITE);

        }
    };

    @OnClick(R.id.button1)
    protected void onClick(){
        ButterKnife.apply(buttons, CHANGE_COLOR);
        //the apply method called on button1 Click
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
}
