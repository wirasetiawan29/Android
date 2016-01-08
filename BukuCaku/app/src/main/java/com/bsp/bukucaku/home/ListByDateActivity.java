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

public class ListByDateActivity extends Activity implements OnItemClickListener {

	Context context = this;
	SharedPreferences sp;
	String register_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listbydate);

		sp = getSharedPreferences("session", Context.MODE_PRIVATE);
		register_id = sp.getString("register_id",null);
		
//		Intent iGetId = getIntent();
//		register_id = iGetId.getStringExtra("register_id");
		if(register_id==null){
			Intent intentLogin = new Intent(context, LoginActivity.class);
			startActivityForResult(intentLogin,1);
		} else {
			loadDatabase(register_id);
		}
//		showMessage(register_id);
//		try {
//			backupDb();
//			showMessage("File successfully created!");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private void loadDatabase(String register_id) {
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
		Cursor cur = db.getDistinctDate(register_id);
		
		String[] from = new String[]{"date"};
		int[] to = new int[]{android.R.id.text1};
		
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_1, cur, 
				from, to);
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		db.close();
	}

	private void showMessage(String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}

	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
//		Cursor cur = db.getDateById(register_id, String.valueOf(id));
		Cursor cur = (Cursor) parent.getItemAtPosition(position);
		if(cur!=null){
//			String date = cur.getString(cur.getColumnIndex("date"));
			String date = cur.getString(0);
//			showMessage(date);
			Intent intent = new Intent(context, ListByDateDetailActivity.class);
			intent.putExtra("date", date);
			startActivity(intent);
		}
	}

//	private void backupDb() throws IOException {
//		File sd = Environment.getExternalStorageDirectory();
//		File data = Environment.getDataDirectory();
//
//		if (sd.canWrite()) {
//
//			String currentDBPath = "/data/data/com.bsp.bukucaku/databases/db_bukucaku.db";
//			String backupDBPath = "/mnt/sdcard/bukucaku/db_bukucaku.db";
//
//			File currentDB = new File(data, currentDBPath);
//			File backupDB = new File(sd, backupDBPath);
//
//			if (backupDB.exists())
//				backupDB.delete();
//
//			if (currentDB.exists()) {
//				makeLogsFolder();
//
//				copy(currentDB, backupDB);
//			}
//
//			dbFilePath = backupDB.getAbsolutePath();
//		}
//	}
//
//	private void makeLogsFolder() {
//		try {
//			File sdFolder = new File(Environment.getExternalStorageDirectory(), dbFilePath);
//			sdFolder.mkdirs();
//		}
//		catch (Exception e) {}
//	}
//
//	private void copy(File from, File to) throws FileNotFoundException, IOException {
//		FileChannel src = null;
//		FileChannel dst = null;
//		try {
//			src = new FileInputStream(from).getChannel();
//			dst = new FileOutputStream(to).getChannel();
//			dst.transferFrom(src, 0, src.size());
//		}
//		finally {
//			if (src != null)
//				src.close();
//			if (dst != null)
//				dst.close();
//		}
//	}
	
	@Override
    protected void onResume() {
        super.onResume();
        loadDatabase(register_id);
    }
}
