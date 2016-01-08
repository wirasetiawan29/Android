package id.or.bandungitclub.tweetme.view;

import id.or.bandungitclub.tweetme.R;
import id.or.bandungitclub.tweetme.R.id;
import id.or.bandungitclub.tweetme.R.layout;
import id.or.bandungitclub.tweetme.R.menu;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RegisterActivity extends ActionBarActivity {

	private RegisterActivity self = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		setTitle("Create Account");
		//opening transition animations
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);		  				
		
		ActionBar actionBar = getSupportActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#23e5cd"));   
		actionBar.setBackgroundDrawable(colorDrawable);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		//closing transition animations
	    overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
	  
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Intent intent = new Intent(self, LoginActivity.class);
			startActivity(intent);
			//overridePendingTransition(R.anim.pull_in_from_left, R.anim.abc_fade_out);			
			self.finish();
			return true;

		default:
			return false;
		}		
	}

}
