package com.bsp.bukucaku.home;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.bsp.bukucaku.LoginActivity;
import com.bsp.bukucaku.R;

public class HomeActivity extends TabActivity {
	
	SharedPreferences sp;
	String emailSession;
	Context context = this;
	String register_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_layout);
		
		sp = getSharedPreferences("session", Context.MODE_PRIVATE);
		emailSession = sp.getString("email", null);
		register_id = sp.getString("register_id",null);
		
//		Intent iGetId = getIntent();
//		register_id = iGetId.getStringExtra("register_id");

		if(emailSession==null && register_id==null){
			Intent intentLogin = new Intent(context, LoginActivity.class);
			startActivityForResult(intentLogin,1);
		} else {
			Resources res = getResources();
			TabHost tabHost = getTabHost();
			TabHost.TabSpec spec;
			Intent intent;

			intent = new Intent().setClass(context,ListByDateActivity.class);
//			intent.putExtra("register_id", register_id);
			spec = tabHost.newTabSpec("By Date").setIndicator("By Date", res.getDrawable(R.drawable.ic_listbydate)).setContent(intent);
			tabHost.addTab(spec);

			intent = new Intent().setClass(context,ListByCategoryActivity.class);
//			intent.putExtra("register_id_for_category", register_id);
			spec = tabHost.newTabSpec("By Category").setIndicator("By Category", res.getDrawable(R.drawable.ic_listbycategory)).setContent(intent);
			tabHost.addTab(spec);
		}
	}
	
	@Override
	public void onBackPressed(){
		this.finish();
	    Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	}
	
	private void showMessage(String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId()==R.id.menu_share){
			String message = "Hi, friends. I'm using Buku Caku Application from PT.BSP. It help us to manage your money. Try It!";
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("text/plain");
			share.putExtra(Intent.EXTRA_TEXT, message);

			startActivity(Intent.createChooser(share, "Share to"));
			return true;
		} else if(item.getItemId()==R.id.menu_logout){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Buku Caku");
			builder.setMessage("Are you sure to log out?");
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Editor editor = sp.edit();
			        editor.clear();
			        editor.commit();
			        Intent intentLogin = new Intent(context, LoginActivity.class);
					startActivity(intentLogin);
					showMessage("Logout successfully!");
				}
			});
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			
			AlertDialog alert = builder.create();
			alert.show();
			return true;
		} else if(item.getItemId()==R.id.menu_add){
			finish();
			Intent intentAdd = new Intent(context, AddExpenseActivity.class);
			startActivity(intentAdd);
			return true;
		}
		
		return false;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if((requestCode==1 || requestCode==2) && (resultCode==RESULT_OK)){
			finish();
			showMessage("Finish");
		}
	}
}
