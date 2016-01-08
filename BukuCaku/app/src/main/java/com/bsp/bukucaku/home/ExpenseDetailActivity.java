package com.bsp.bukucaku.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bsp.bukucaku.LoginActivity;
import com.bsp.bukucaku.R;
import com.bsp.bukucaku.db.DbBukuCaku;

public class ExpenseDetailActivity extends Activity {

	Context context = this;
	int id;
	
	TextView textViewNominal;
	TextView textViewDate;
	TextView textViewCategory;
	TextView textViewDesc;
	
	Button buttonEdit;
	Button buttonDelete;
	Button buttonCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_expensedetail);
		
		textViewNominal = (TextView) findViewById(R.id.textViewNominal);
		textViewDate = (TextView) findViewById(R.id.textViewDate);
		textViewCategory = (TextView) findViewById(R.id.textViewCategory);
		textViewDesc = (TextView) findViewById(R.id.textViewDesc);
		
		buttonEdit = (Button) findViewById(R.id.buttonEdit);
		buttonDelete = (Button) findViewById(R.id.buttonDelete);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		
		Intent intent = getIntent();
		id = intent.getExtras().getInt("id");
		
		loadDatabase(id);
		
		buttonEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				Intent intent = new Intent(context, AddExpenseActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
		
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteDataPengeluaran(id);
			}
		});
		
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void loadDatabase(int id) {
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
		Cursor cur = db.getExpenseById(id);
		if(cur != null) {
			cur.moveToFirst();
			textViewNominal.setText("Rp" + cur.getString(cur.getColumnIndex("nominal")));
			textViewDate.setText(cur.getString(cur.getColumnIndex("date")));
			textViewCategory.setText(cur.getString(cur.getColumnIndex("category_name")));
			textViewDesc.setText(cur.getString(cur.getColumnIndex("desc")));
		}
		db.close();
	}
	
	private void deleteDataPengeluaran(int id) {
		final int ID = id;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Buku Caku");
		builder.setMessage("Are you sure to delete this record?");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DbBukuCaku db = new DbBukuCaku(context);
				db.open();
				db.deleteDataExpense(ID);
				db.close();
				showMessage("Delete successfully!");
				finish();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showMessage(String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}
}
