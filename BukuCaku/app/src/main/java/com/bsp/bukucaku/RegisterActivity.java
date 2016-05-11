package com.bsp.bukucaku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bsp.bukucaku.db.DbBukuCaku;
import com.bsp.bukucaku.home.HomeActivity;

public class RegisterActivity extends Activity {
	
	Context context = this;
	private static final String[] DUMMY_CREDENTIALS = new String[] {
		"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the SignUp task to ensure we can cancel it if requested.
	 */
	private UserSignUpTask mAuthTask = null;
	
	// Values for email and password at the time of the sign up attempt.
	private String mName;
	private String mEmail;
	private String mPassword;
	private String mConfirmPassword;

	// UI references.
	private EditText mNameView;
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mConfirmPasswordView;
	
	private View mSignUpFormView;
	private View mSignUpStatusView;
	private TextView mSignUpStatusMessageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		mNameView = (EditText) findViewById(R.id.name);
		mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
		mEmailView = (EditText) findViewById(R.id.email);
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
		.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.signup || id == EditorInfo.IME_NULL) {
					attemptSignUp();
					return true;
				}
				return false;
			}
		});

		mSignUpFormView = findViewById(R.id.signup_form);
		mSignUpStatusView = findViewById(R.id.signup_status);
		mSignUpStatusMessageView = (TextView) findViewById(R.id.signup_status_message);

		findViewById(R.id.sign_up_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptSignUp();
					}
				});
	}
	
	/**
	 * Attempts to sign in or register the account specified by the SignUp form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual SignUp attempt is made.
	 */
	public void attemptSignUp() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mNameView.setError(null);
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mConfirmPasswordView.setError(null);

		// Store values at the time of the SignUp attempt.
		mName = mNameView.getText().toString();
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mConfirmPassword= mConfirmPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;
		
		if (TextUtils.isEmpty(mConfirmPassword)) {
			mConfirmPasswordView.setError(getString(R.string.error_field_required));
			focusView = mConfirmPasswordView;
			cancel = true;
		} else if (!mConfirmPassword.equals(mPassword)) {
			mConfirmPasswordView.setError(getString(R.string.error_incorrect_password_register));
			focusView = mConfirmPasswordView;
			cancel = true;
		}
		
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password_register));
			focusView = mPasswordView;
			cancel = true;
		}
		
		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		
		// Check for a valid password.
		if(TextUtils.isEmpty(mName)){
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt SignUp and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user SignUp attempt.
			mSignUpStatusMessageView.setText(R.string.signup_progress_signing_up);
			showProgress(true);
			mAuthTask = new UserSignUpTask();
			mAuthTask.execute((Void) null);
		}
	}
	
	/**
	 * Represents an asynchronous SignUp/registration task used to authenticate
	 * the user.
	 */
	public class UserSignUpTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			// Simulate network access.
//				Thread.sleep(2000);
			insertDatabase();

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				showMessage("Register Succesfully!");
				Intent intent = new Intent(context, LoginActivity.class);
				startActivity(intent);
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
	
	/**
	 * Shows the progress UI and hides the SignUp form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mSignUpStatusView.setVisibility(View.VISIBLE);
			mSignUpStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSignUpStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mSignUpFormView.setVisibility(View.VISIBLE);
			mSignUpFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSignUpFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mSignUpStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	private void insertDatabase(){
		DbBukuCaku db = new DbBukuCaku(context);
		db.open();
        db.insertDataRegister(mName, mEmail, mPassword);
        db.close();
	}
	
	private void showMessage(String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}
}
