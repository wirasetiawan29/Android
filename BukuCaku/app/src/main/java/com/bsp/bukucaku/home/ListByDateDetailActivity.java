package com.bsp.bukucaku.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bsp.bukucaku.R;
import com.bsp.bukucaku.db.DbBukuCaku;

public class ListByDateDetailActivity extends Activity implements OnItemClickListener{
	
	Context context = this;
	TextView textViewDate;
	TextView textViewTotal;
	String date;
	SharedPreferences sp;
	String register_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listbydatedetail);
		
		sp = getSharedPreferences("session", Context.MODE_PRIVATE);
		register_id = sp.getString("register_id",null);
		
		textViewDate = (TextView) findViewById(R.id.textViewDate);
		textViewTotal = (TextView) findViewById(R.id.textViewTotal);
		
		Intent intent = getIntent();
		date= intent.getStringExtra("date");
		
		textViewDate.setText(date);
		loadDatabase(date,register_id);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		Intent intent = new Intent(context, ExpenseDetailActivity.class);
		intent.putExtra("id", (int) id);
        startActivity(intent);
	}
	
	private void showMessage(String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}
	
	private void loadDatabase(String date, String registerId) {
		String[] from = new String[]{"nominal", "desc"};
		int[] to = new int[]{android.R.id.text1, android.R.id.text2};
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
		Cursor cur = db.getListDetailByDate(date,registerId);
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_2, cur, 
				from, to);
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		Cursor totalExpenseByDate = db.getTotalExpenseByDate(date);
		totalExpenseByDate.moveToFirst();
		textViewTotal.setText("Total: Rp" + totalExpenseByDate.getString(totalExpenseByDate.getColumnIndex("total_nominal")));
		db.close();
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        loadDatabase(date,register_id);
    }
}
