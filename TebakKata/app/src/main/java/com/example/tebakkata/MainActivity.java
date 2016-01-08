package com.example.tebakkata;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	Spinner spinnerCategory;
	String[] arrayCategory;
	int categoryIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		arrayCategory = getResources().getStringArray(R.array.category);
		spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayCategory);
		spinnerCategory.setAdapter(adapter);
		
		spinnerCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, 
			View arg1, int arg2, long arg3)
			{
				int index = spinnerCategory.getSelectedItemPosition();
				categoryIndex = index;
			}
			
			public void onNothingSelected(AdapterView<?> arg0){}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onClickStart(View v)
	{
		Intent i = new Intent(this, Hangman.class);
		i.putExtra("inde", categoryIndex);
		startActivity(i);
	}
	
	public void onClickAbout (View v)
	{
		Toast.makeText(MainActivity.this, "About This Application", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickExit (View v)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Exit");
		builder.setMessage("Are you sure to Exit ?");
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
				java.lang.System.exit(0);
			}
		});
		
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		AlertDialog alert = builder.create();
		alert.show();
	}

}
