package id.or.bandungitclub.tweetme.view;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import id.or.bandungitclub.tweetme.R;
import id.or.bandungitclub.tweetme.connection.AsynConnection;
import id.or.bandungitclub.tweetme.connection.AsynConnection.RequestType;
import id.or.bandungitclub.tweetme.connection.ConnectionInterface;
import id.or.bandungitclub.tweetme.connection.Helper;
import id.or.bandungitclub.tweetme.utils.AppConstant;
import id.or.bandungitclub.tweetme.utils.TransparentProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity implements ConnectionInterface {

	private LoginActivity self = this;
	private TransparentProgressDialog progressDialog;
	private AsynConnection connection;
	private SharedPreferences sessions;
	private IntentLauncher launcher;
	private TextView tvRegister;
	private EditText etUsername, etPassword;
	private Button btnLogin;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//opening transition animations
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);		  
		
		ActionBar actionBar = getSupportActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#23e5cd"));   
		actionBar.setBackgroundDrawable(colorDrawable);
		
		sessions = getSharedPreferences("SESSION", 0);
		tvRegister = (TextView) findViewById(R.id.tvRegister);
		tvRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(self, RegisterActivity.class);
				startActivity(intent);
				//overridePendingTransition(R.anim.pull_in_from_left, R.anim.abc_fade_out);
				self.finish();
			}
		});
		
		launcher = new IntentLauncher();
		launcher.start();
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void doLogin(){
		etUsername = (EditText) findViewById(R.id.etLoginUsername);
		etPassword = (EditText) findViewById(R.id.etLoginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(etUsername.getText().toString().matches("") || etPassword.getText().toString().matches("")){
					Toast.makeText(self, "Please Input Username and Password", Toast.LENGTH_SHORT).show();
				} else {
					loginAction(AppConstant.URL_LOGIN, etUsername.getText().toString(), etPassword.getText().toString());
				}
			}
		});
	}
	
	private void loginAction(String url, String username, String password){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(self, R.drawable.loader);
			progressDialog.show();
			
			Log.i("Login URL", url);

			connection = new AsynConnection(self, url, 1);			
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("username", username);
			param.put("password", password);
			
			connection.asyncConnectRequest(null, param, RequestType.POST);
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

							jObj = jObj.getJSONObject("data");
							String uid = jObj.getString("user_id");
							Log.i("uid", uid);
							
							SharedPreferences.Editor edit = sessions.edit();
							edit.putString("username", etUsername.getText().toString());
							edit.putString("password", etPassword.getText().toString());
							edit.putString("uid", uid);	
							edit.putBoolean("isLogin", true);
							edit.commit();
									
							Intent intent = new Intent(self, TimelineActivity.class);
							startActivity(intent);
							self.finish();
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
				Log.i("Error Response", value.toString().trim());
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

	private class IntentLauncher extends Thread {
		@Override
		public void run() {
			
			if(sessions.contains("isLogin")){
				Intent intent = new Intent(self, TimelineActivity.class);
				startActivity(intent);
				self.finish();
			} else{					
				doLogin();
			}
			
		}		
	}
}
