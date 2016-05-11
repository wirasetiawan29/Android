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
import android.widget.Toast;

import com.bsp.bukucaku.LoginActivity;
import com.bsp.bukucaku.R;
import com.bsp.bukucaku.db.DbBukuCaku;

public class ListByCategoryActivity extends Activity implements OnItemClickListener {

	Context context = this;
	SharedPreferences sp;
	String register_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listbycategory);
		
		sp = getSharedPreferences("session", Context.MODE_PRIVATE);
		register_id = sp.getString("register_id",null);
		
//		Intent iGetId = getIntent();
//		register_id = iGetId.getStringExtra("register_id_for_category");
		if(register_id==null){
			Intent intentLogin = new Intent(context, LoginActivity.class);
			startActivityForResult(intentLogin,2);
		} else {
			loadDatabase(register_id);
		}
	}
	
	private void showMessage(String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}
	
	private void loadDatabase(String register_id) {
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
		Cursor cur = db.getListByCategory(register_id);

		String[] from = new String[]{"category_name", "nominal"};
		int[] to = new int[]{android.R.id.text1, android.R.id.text2};
		
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_2, cur, 
				from, to);
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		db.close();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
		Cursor cur = db.getCategoryById(String.valueOf(id));
		if(cur!=null){
			cur.moveToFirst();
			String category = cur.getString(cur.getColumnIndex("category_name"));
			Intent intent = new Intent(context, ListByCategoryDetailActivity.class);
			intent.putExtra("category", category);
			startActivity(intent);
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        loadDatabase(register_id);
    }
}
