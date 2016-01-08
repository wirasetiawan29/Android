package id.or.bandungitclub.tweetme.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Singleton {
	
	private static Singleton singleton;
	
	public Singleton() {
		// TODO Auto-generated constructor stub
	}
	
	public static Singleton getInstance(){
		if(singleton == null){
			singleton = new Singleton();
		}
		return singleton;
	}
		
	public String getDefaultPreferences(Context activity, String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity);
		String result = sharedPreferences.getString(key, null);
		return result;
	}
	
	public String getStringPreferences(Context ctx, String key) {
		SharedPreferences sharedPreferences = ctx.getSharedPreferences("SESSION", 0);
		String result = sharedPreferences.getString(key, null);
		return result;
	}
	
}
