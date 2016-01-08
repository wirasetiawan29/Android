package id.or.bandungitclub.tweetme.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import id.or.bandungitclub.tweetme.R;
import id.or.bandungitclub.tweetme.adapter.DrawerAdapter;
import id.or.bandungitclub.tweetme.adapter.DrawerItem;
import id.or.bandungitclub.tweetme.adapter.TimelineListAdapter;
import id.or.bandungitclub.tweetme.connection.AsynConnection;
import id.or.bandungitclub.tweetme.connection.AsynConnection.RequestType;
import id.or.bandungitclub.tweetme.connection.ConnectionInterface;
import id.or.bandungitclub.tweetme.connection.Helper;
import id.or.bandungitclub.tweetme.model.Timeline;
import id.or.bandungitclub.tweetme.parser.TimelineParser;
import id.or.bandungitclub.tweetme.utils.AppConstant;
import id.or.bandungitclub.tweetme.utils.Singleton;
import id.or.bandungitclub.tweetme.utils.TransparentProgressDialog;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineActivity extends ActionBarActivity implements ConnectionInterface {

	private TimelineActivity self = this;
	private ListView listViewTimeline;
	private TransparentProgressDialog progressDialog;
	private AsynConnection connection;
	private SharedPreferences sessions;
	private ActionBar mActionBar;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerAdapter drawerAdapter;
	private List<DrawerItem> dataList;
	private String uid;
	private Dialog dialog;
	private EditText etPostTweet;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setTitle("Timeline");
		
		//opening transition animations
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);		  				
						
		// initialize actionbar
		mActionBar = getSupportActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#23e5cd"));   
		mActionBar.setBackgroundDrawable(colorDrawable);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);

		// initialize drawer layout
		dataList = new ArrayList<DrawerItem>();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		dataList.add(new DrawerItem("Home", R.drawable.ic_launcher));
		dataList.add(new DrawerItem("Profile", R.drawable.ic_launcher));
		dataList.add(new DrawerItem("Search Friends", R.drawable.ic_launcher));
		dataList.add(new DrawerItem("Log out", R.drawable.ic_launcher));
		
		drawerAdapter = new DrawerAdapter(this, R.layout.drawer_item,dataList);
		mDrawerList.setAdapter(drawerAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, 
                R.string.app_name,R.string.app_name);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		sessions = getSharedPreferences("SESSION", 0);
		listViewTimeline = (ListView) findViewById(R.id.lvTimeline);
		uid = Singleton.getInstance().getStringPreferences(self, "uid");
		timelineAction(AppConstant.URL_TIMELINE + uid);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@SuppressLint("NewApi")
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {      
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_actionbar, menu);
	    return super.onCreateOptionsMenu(menu);        
    }
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		int id = item.getItemId();
		switch (id) {
		case R.id.action_new:	
			
			dialog = new Dialog(self, R.style.TransparentProgressDialog);
			dialog.setContentView(R.layout.dialog_post_tweet);
			dialog.show();
			
			Button btnPostTweet = (Button) dialog.findViewById(R.id.btnPostTweet);
			btnPostTweet.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					tweetAction(AppConstant.URL_TWEET);
				}
			});
			
			final TextView tvPostTweetLong = (TextView) dialog.findViewById(R.id.tvPostTweetLong);
			
			etPostTweet = (EditText) dialog.findViewById(R.id.etPostTweetMsg);
			etPostTweet.setOnKeyListener(new View.OnKeyListener() {
				
				@Override
				public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
					// TODO Auto-generated method stub
					tvPostTweetLong.setText(String.valueOf(etPostTweet.getText().toString().length()) + " characters");					
					return false;
				}
			});
			return true;
		case R.id.action_refresh:			
			timelineAction(AppConstant.URL_TIMELINE + uid);			
			return true;
		default:
			return false;
		}
	}
	
	private void timelineAction(String url){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(self, R.drawable.loader);
			progressDialog.show();
			
			Log.i("Timeline URL", url);

			connection = new AsynConnection(self, url, 1);
			connection.asyncConnectRequest(null, null, RequestType.GET);
		} else {
			Toast.makeText(self, "No Internet Connection", Toast.LENGTH_SHORT).show();	
		}
	}
	
	private void tweetAction(String url){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(self, R.drawable.loader);
			progressDialog.show();
			
			Log.i("Tweet URL", url);

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("user_id", uid);
			params.put("message", etPostTweet.getText().toString());
			
			connection = new AsynConnection(self, url, 2);
			connection.asyncConnectRequest(null, params, RequestType.POST);
		} else {
			Toast.makeText(self, "No Internet Connection", Toast.LENGTH_SHORT).show();	
		}
	}
	
	@Override
	public void callBackOnSuccess(final Object value, int responseCode, final int type) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
		self.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("Response", value.toString().trim());
				switch (type) {
				case 1:
					try {
						JSONObject jObj = new JSONObject(value.toString().trim());
						int responseCode = jObj.getInt("errorCode");
						if(responseCode == 0){
							TimelineParser timelineParser = new TimelineParser();
							Vector<Timeline> vTimeline = timelineParser.parseTimeline(value.toString().trim());
							
							TimelineListAdapter timelineAdapter = new TimelineListAdapter(self, vTimeline);
							listViewTimeline.setAdapter(timelineAdapter);							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						JSONObject jObj = new JSONObject(value.toString().trim());
						int responseCode = jObj.getInt("errorCode");
						if(responseCode == 0){
							dialog.dismiss();
							String msg = jObj.getString("message");
							Toast.makeText(self, msg, Toast.LENGTH_SHORT).show();	
							timelineAction(AppConstant.URL_TIMELINE + uid);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				default:
					break;
				}
			}
		});

	}

	@Override
	public void callBackOnFailed(final Object value, int responseCode, int type) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
		self.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					JSONObject jObj = new JSONObject(value.toString().trim());
					String msg = jObj.getString("message");
					Toast.makeText(self, msg, Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			}
		});
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			switch (position) {
			case 0:
				timelineAction(AppConstant.URL_TIMELINE + uid);
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				SharedPreferences.Editor edit = sessions.edit();
				edit.clear();
				edit.commit();			
				
				Intent intent = new Intent(self, LoginActivity.class);
				startActivity(intent);
				self.finish();
				break;
			default:
				break;
			}
		}
	}

}
