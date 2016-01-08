package com.bsp.bukucaku.home;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bsp.bukucaku.R;
import com.bsp.bukucaku.db.DbBukuCaku;

public class AddExpenseActivity extends Activity {

	Context context = this;
	int categoryId;
	Calendar myCalendar = Calendar.getInstance();
	DateFormat dt = new SimpleDateFormat("dd MMMM yyyy");
	int idForUpdate = 0;
	SharedPreferences sp;
	String register_id;

	Spinner spinnerCategory;
	EditText editTextDate;
	EditText editTextNominal;
	EditText editTextDesc;
	Button buttonSave;
	Button buttonEdit;

	private double mNominal;
	private String mDate;
	private String mDesc;
	private int mCategoryId;

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH ,monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateDate();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addexpense);
		
		sp = getSharedPreferences("session", Context.MODE_PRIVATE);
		register_id = sp.getString("register_id",null);
		
		try {
			Intent intent = getIntent();
			idForUpdate = intent.getExtras().getInt("id");
		} catch (Exception e) {
			// TODO: handle exception
		}

		spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
		editTextNominal = (EditText) findViewById(R.id.editTextNominal);
		editTextDate = (EditText) findViewById(R.id.editTextDate);
		editTextDesc = (EditText) findViewById(R.id.editTextDesc);
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonEdit = (Button) findViewById(R.id.buttonEdit);
		
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
		Cursor cur = db.getAllKategori();
		if(cur!=null){
			String[] from = new String[]{"category_name"};
			int[] to = new int[]{android.R.id.text1};
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
					android.R.layout.simple_list_item_1, cur, 
					from, to);
			spinnerCategory.setAdapter(adapter);	
			spinnerCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
					int index = spinnerCategory.getSelectedItemPosition();
					categoryId = index;
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
		db.close();
		
		if(idForUpdate != 0){
			setInputFromDatabase(idForUpdate);
			buttonSave.setVisibility(View.GONE);
			buttonEdit.setVisibility(View.VISIBLE);
		} else {
			buttonEdit.setVisibility(View.GONE);
			buttonSave.setVisibility(View.VISIBLE);
		}
		
		editTextDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hideSoftKeyBoard(v);
				new DatePickerDialog(context,date,
						myCalendar.get(Calendar.YEAR),
						myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		buttonSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				saveData();
			}
		});
		
		buttonEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				updateData();
			}
		});
	}

	private void updateDate(){
		editTextDate.setText(dt.format(myCalendar.getTime()));
	}

	private void showMessage(String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}

	private void saveData(){
		boolean cancel = false;
		
		mDate = editTextDate.getText().toString();
		mDesc = editTextDesc.getText().toString();
		mCategoryId = categoryId+1;
		
		if (TextUtils.isEmpty(editTextNominal.getText().toString())) {
			showMessage("Nominal must be filled");
			cancel = true;
		} else {
			mNominal = Double.parseDouble(editTextNominal.getText().toString());
		}
		
		if (TextUtils.isEmpty(mDate)) {
			showMessage("Date must be filled");
			cancel = true;
		} 
		
		if (TextUtils.isEmpty(mDesc)) {
			showMessage("Description must be filled");
			cancel = true;
		}
		
//		showMessage(String.valueOf(mNominal));
//		showMessage(mDate);
//		showMessage(mDesc);
//		showMessage(String.valueOf(mCategoryId));
		if(cancel){
			showMessage("All field are required!");
		} else {
			DbBukuCaku db = new DbBukuCaku(context);
			db.open();
			db.insertDataExpense(mNominal, mDate, mDesc, mCategoryId, register_id);
			db.close();
			showMessage("Input Successfully!");
			finish();
			Intent intent = new Intent(context, HomeActivity.class);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			startActivity(intent);
		}
	}
	
	private void updateData(){
boolean cancel = false;
		
		mDate = editTextDate.getText().toString();
		mDesc = editTextDesc.getText().toString();
		mCategoryId = categoryId+1;
		
		if (TextUtils.isEmpty(editTextNominal.getText().toString())) {
			showMessage("Nominal must be filled");
			cancel = true;
		} else {
			mNominal = Double.parseDouble(editTextNominal.getText().toString());
		}
		
		if (TextUtils.isEmpty(mDate)) {
			showMessage("Date must be filled");
			cancel = true;
		} 
		
		if (TextUtils.isEmpty(mDesc)) {
			showMessage("Description must be filled");
			cancel = true;
		}
		
//		showMessage(String.valueOf(mNominal));
//		showMessage(mDate);
//		showMessage(mDesc);
//		showMessage(String.valueOf(mCategoryId));
		if(cancel){
			showMessage("All field are required!");
		} else {
			DbBukuCaku db = new DbBukuCaku(context);
			db.open();
			db.updateDataExpense(idForUpdate, mNominal, mDate, mDesc, mCategoryId);
			db.close();
			showMessage("Update Successfully!");
			finish();
		}
		
//		Intent intent = new Intent(context, ListByDateDetailActivity.class);
//		intent.addCategory(Intent.CATEGORY_DEFAULT);
//		startActivity(intent);
	}
	
	private void hideSoftKeyBoard(View v) {
        if (v != null && context!=null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
	
	public void onBackPressed(){
		finish();
		Intent intent = new Intent(context, HomeActivity.class);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		startActivity(intent);
	}
	
	private void setInputFromDatabase(int id) {
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
		Cursor cur = db.getExpenseById(id);
		if(cur != null) {
			cur.moveToFirst();
			editTextNominal.setText(cur.getString(cur.getColumnIndex("nominal")));
			editTextDate.setText(cur.getString(cur.getColumnIndex("date")));
			int index = Integer.parseInt(cur.getString(cur.getColumnIndex("category_id")))-1;
			spinnerCategory.setSelection(index);
			editTextDesc.setText(cur.getString(cur.getColumnIndex("desc")));
		}
		db.close();
	}
}
