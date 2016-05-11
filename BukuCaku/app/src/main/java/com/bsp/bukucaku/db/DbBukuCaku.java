package com.bsp.bukucaku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

public class DbBukuCaku {
	private SQLiteDatabase db;
	private final Context con;
	private final DbOpenHelper dbHelper;
	
	public DbBukuCaku(Context con) {
		this.con = con;
		dbHelper = new DbOpenHelper(this.con, "", null, 0);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		db.close();
	}
	
	public long insertDataRegister(String name, String email, String password) {
		ContentValues newValues = new ContentValues();
		newValues.put("name", name);
		newValues.put("email", email);
		newValues.put("password", password);
		
		return db.insert("tb_register", null, newValues);	
	}
	
	public long insertDataExpense(double nominal, String date, String desc, int categoryId, String registerId) {
		ContentValues newValues = new ContentValues();
		newValues.put("nominal", nominal);
		newValues.put("date", date);
		newValues.put("desc", desc);
		newValues.put("category_id", categoryId);
		newValues.put("register_id", registerId);
		
		return db.insert("tb_pengeluaran", null, newValues);	
	}
	
	public long updateDataExpense(int id, double nominal, String date, String desc, int categoryId) {
		ContentValues newValues = new ContentValues();
		newValues.put("nominal", nominal);
		newValues.put("date", date);
		newValues.put("desc", desc);
		newValues.put("category_id", categoryId);
		
		return db.update("tb_pengeluaran", newValues, "_id = " + id, null);	
	}
	
	public long deleteDataExpense(int id) {
		return db.delete("tb_pengeluaran", "_id = " + id, null);	
	}
	
	public void updateDataRegister(int id, String name, String email, String password) {
		ContentValues newValues = new ContentValues();
		newValues.put("name", name);
		newValues.put("email", email);
		newValues.put("password", password);
		
		db.update("tb_register", newValues, "_id = " + id, null);
	}
	
	public Cursor getDataRegister(){
		return db.query("tb_register", null, null, null,null,null,null);
		//return db.rawQuery("SELECT * FROM data_taxi", null);
	}
	
	public Cursor getEmail(String id){
		return db.query("tb_register", null, "_id = '" + id +"'", null,null,null,null);
		//return db.rawQuery("SELECT * FROM data_taxi", null);
	}
	
	public Cursor checkLogin(String email, String password){
		return db.rawQuery("SELECT * FROM tb_register WHERE email=? AND password=?", new String[] {email, password});
	}
	
	public Cursor getListByDate(){
//		return db.rawQuery("SELECT DISTINCT date as _id FROM tb_pengeluaran", null);
		return db.query("tb_pengeluaran", null, null, null, null, null, null);
	}
	
	public Cursor getListDetailByDate(String date, String registerId){
//		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
//		builder.setTables("tb_pengeluaran tb INNER JOIN tb_kategori tk ON (tp.category_id = tk._id)");
//		return db.query(builder.getTables(), null, null, null, null, null, "date DESC");
//		return db.rawQuery("SELECT * FROM tb_pengeluaran WHERE category_id=? ORDER BY date desc", new String[] {categoryId});
		return db.rawQuery("SELECT tp._id, tp.date, tk.category_name, tp.desc, tp.nominal FROM tb_pengeluaran tp, tb_kategori tk, tb_register tr WHERE tp.date= ? AND tp.register_id = ? AND tp.category_id = tk._id AND tp.register_id = tr._id", new String[] {date, registerId});
	}
	
	public Cursor getListDetailByCategory(String category, String registerId){
		return db.rawQuery("SELECT tp._id, tp.date, tk.category_name, tp.desc, tp.nominal FROM tb_pengeluaran tp, tb_kategori tk, tb_register tr WHERE tk.category_name=? AND tp.register_id = ? AND tp.category_id = tk._id AND tp.register_id = tr._id", new String[] {category,registerId});
	}
	
	public Cursor getDistinctDate(String register_id){
		return db.rawQuery("SELECT DISTINCT tp.date, tp._id, tr._id FROM tb_pengeluaran tp, tb_register tr WHERE tp.register_id = tr._id AND tp.register_id = ? GROUP BY date ORDER BY date DESC",new String[] {register_id});
	}
	
	public Cursor getAllKategori(){
		return db.query("tb_kategori", null, null, null,null,null,null);
//		return db.query("tb_kategori", null, null, null, null, null, null);
	}
	
	public Cursor getListByCategory(String register_id){
//		return db.rawQuery("SELECT tk.category_name, sum(tp.nominal) nominal, tk._id, tp.register_id FROM tb_kategori tk INNER JOIN tb_pengeluaran tp  ON tp.category_id = tk._id INNER JOIN tb_register tr ON tp.register_id = tr._id AND tp.register_id = ? GROUP BY tk.category_name",new String[] {register_id});
		return db.rawQuery("SELECT tk.category_name, sum(tp.nominal) nominal, tp.register_id, tk._id FROM tb_kategori tk, tb_pengeluaran tp, tb_register tr WHERE tp.category_id = tk._id AND tp.register_id = tr._id AND tp.register_id = ? GROUP BY tk.category_name",new String[] {register_id});
	}
	
	public Cursor getDateById(String register_id, String date){
		//return db.rawQuery("SELECT _id, date FROM tb_pengeluaran WHERE _id = ?", new String[] {id});
		return db.rawQuery("SELECT DISTINCT tp.date, tp._id, tr._id FROM tb_pengeluaran tp, tb_register tr WHERE tp.register_id = tr._id AND tp.register_id = ? AND tp.date = ? GROUP BY date ORDER BY date DESC", new String[] {register_id, date});
	}
	
	public Cursor getCategoryById(String id){
		return db.rawQuery("SELECT _id, category_name FROM tb_kategori WHERE _id = ?", new String[] {id});
	}
	
	public Cursor getExpenseById(int id){
		return db.rawQuery("SELECT tp.nominal, tp.date, tp.category_id, tk.category_name, tp.desc FROM tb_pengeluaran tp, tb_kategori tk WHERE tp.category_id =  tk._id AND tp._id = ?", new String[] {String.valueOf(id)});
	}
	
	public Cursor getTotalExpenseByDate(String date){
		return db.rawQuery("SELECT _id, sum(nominal) total_nominal from tb_pengeluaran where date=?", new String[] {date});
	}
	
	public void resetDb(){
		dbHelper.onUpgrade(db, 0, 0);
	}
}
