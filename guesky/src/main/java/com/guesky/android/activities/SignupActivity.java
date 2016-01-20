package com.guesky.android.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.guesky.android.R;
import com.guesky.android.tasks.SignupTask;

public class SignupActivity extends Activity {
	
	private Button register_btn;
	private String email,password,confirm_password,username;
	private EditText email_et,password_et,confirm_password_et,username_et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		register_btn = (Button)findViewById(R.id.register_btn);
		email_et = (EditText) findViewById(R.id.email);
		password_et = (EditText) findViewById(R.id.password);
		confirm_password_et =  (EditText) findViewById(R.id.confirm_password);
		username_et = (EditText) findViewById(R.id.username);
		
		register_btn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				String email = email_et.getText().toString();
			    String password = password_et.getText().toString();
			    String confirm_password = confirm_password_et.getText().toString();
			    String username = username_et.getText().toString();
				if(isNetworkOnline()){
					if(email.equals("")||password.equals("")||confirm_password.equals("")||username.equals("")){
						Toast.makeText(getApplicationContext(),
			                      "Please make sure all fields has been filled out", Toast.LENGTH_SHORT).show();
					//check email format
					}else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
						Toast.makeText(getApplicationContext(),
			                      "Please provide a valid email address", Toast.LENGTH_SHORT).show();
					//check if passwords matches
					}else if(!confirm_password.equals(password)){
						Toast.makeText(getApplicationContext(),
			                      "Passwords are different", Toast.LENGTH_SHORT).show();
					}else if(username.length() > 100){
						Toast.makeText(getApplicationContext(),
			                      "Your name is too long, make sure it less than 100 characters", Toast.LENGTH_SHORT).show();
					}else{
						new SignupTask(SignupActivity.this,SignupActivity.this).execute(email,password,username);
					}
				}else{
			    	  Toast.makeText(getApplicationContext(),
		                      "No network connection", Toast.LENGTH_SHORT).show();
			    }	
			}
		});	
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
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
	
	//check network connectivity
	public boolean isNetworkOnline() {
	    boolean status=false;
	    try{
	        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getNetworkInfo(0);
	        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
	            status= true;
	        }else {
	            netInfo = cm.getNetworkInfo(1);
	            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	                status= true;
	        }
	    }catch(Exception e){
	        e.printStackTrace();  
	        return false;
	    }
	    return status;
	} 
}
