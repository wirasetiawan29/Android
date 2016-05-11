package com.bsp.bukucaku.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "db_bukucaku.db";
	private static final String TABLE_REGISTER_CREATE = 
		"CREATE TABLE tb_register (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT)";
	
	private static final String TABLE_KATEGORI_CREATE = 
			"CREATE TABLE tb_kategori (_id INTEGER PRIMARY KEY AUTOINCREMENT, category_name TEXT)";
	
	private static final String TABLE_PENGELUARAN_CREATE = 
			"CREATE TABLE tb_pengeluaran (_id INTEGER PRIMARY KEY AUTOINCREMENT, nominal REAL, date DATE, desc TEXT, category_id INTEGER NULL, register_id INTEGER NULL)";
	
	public DbOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_REGISTER_CREATE);
		db.execSQL(TABLE_KATEGORI_CREATE);
		db.execSQL(TABLE_PENGELUARAN_CREATE);
		
		ContentValues cv = new ContentValues();
		cv.put("name", "Rendy");
		cv.put("email", "rendy.faqot@gmail.com");
		cv.put("password", "12345");
		db.insert("tb_register", null, cv);
//		
//		cv.put("name", "Rendy");
//		cv.put("email", "rendy.faqot@yahoo.com");
//		cv.put("password", "12345");
//		db.insert("tb_register", null, cv);
//		
		cv = new ContentValues();
		cv.put("category_name", "Belanja");
		db.insert("tb_kategori", null, cv);
		
		cv = new ContentValues();
		cv.put("category_name", "Hiburan");
		db.insert("tb_kategori", null, cv);
		
		cv = new ContentValues();
		cv.put("category_name", "Makan");
		db.insert("tb_kategori", null, cv);
		
		cv = new ContentValues();
		cv.put("category_name", "Olahraga");
		db.insert("tb_kategori", null, cv);
		
		cv = new ContentValues();
		cv.put("category_name", "Transportasi");
		db.insert("tb_kategori", null, cv);
		
		cv = new ContentValues();
		cv.put("category_name", "Lain-lain");
		db.insert("tb_kategori", null, cv);
//		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy"); 
//		Calendar myCalendar = Calendar.getInstance();
//		myCalendar.add(Calendar.DATE, -1);
//		
//		cv = new ContentValues();
//		cv.put("nominal", 2000.00);
//		cv.put("date", dateFormat.format(myCalendar.getTime()));
//		cv.put("desc", "Makan siang di The Cost");
//		cv.put("category_id",3);
//		cv.put("register_id", 1);
//		db.insert("tb_pengeluaran", null, cv);
//		
//		cv = new ContentValues();
//		cv.put("nominal", 40000.00);
//		cv.put("date", dateFormat.format(myCalendar.getTime()));
//		cv.put("desc", "Beli sandal");
//		cv.put("category_id",1);
//		cv.put("register_id",1);
//		db.insert("tb_pengeluaran", null, cv);
//		
//		myCalendar.add(Calendar.DATE, -1);
//		
//		cv = new ContentValues();
//		cv.put("nominal", 50000.00);
//		cv.put("date", dateFormat.format(myCalendar.getTime()));
//		cv.put("desc", "Beli baju");
//		cv.put("category_id",1);
//		cv.put("register_id",1);
//		db.insert("tb_pengeluaran", null, cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		db.execSQL("DROP TABLE IF EXISTS tb_register");
		db.execSQL("DROP TABLE IF EXISTS tb_kategori");
		db.execSQL("DROP TABLE IF EXISTS tb_pengeluaran");
		onCreate(db);
	}

}
