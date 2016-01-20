package com.guesky.android.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.guesky.android.operations.SessionManager;
import com.guesky.android.tasks.LoginTask;

public class LoginActivity extends Activity {

	private EditText email_et,password_et;
	private Button login_btn,reset_btn,signup_btn;
	private String email,password;
	private SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		email_et = (EditText) findViewById(R.id.email);
		password_et = (EditText) findViewById(R.id.password);
		
		login_btn = (Button) findViewById(R.id.login_btn);
		reset_btn = (Button) findViewById(R.id.reset_btn);
		signup_btn = (Button) findViewById(R.id.signup_btn);
		
		//Session check
		session = new SessionManager(getApplicationContext());
		if(session.checkLogin()){
			startActivity(new Intent(getApplicationContext(),Main.class));
			finish();
		}
		
		//login button click listener
	    login_btn.setOnClickListener(
				new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
					      String email = email_et.getText().toString();
					      String password = password_et.getText().toString();
					      if(isNetworkOnline()){
					    	  if (  ( !email_et.getText().toString().equals("")) && ( !password_et.getText().toString().equals("")) )
				                {
					    		    new LoginTask(LoginActivity.this,LoginActivity.this,session).execute(email,password);
				                }else if ( ( !email_et.getText().toString().equals("")) ){
				                    Toast.makeText(getApplicationContext(),
				                            "Please enter your password", Toast.LENGTH_SHORT).show();
				                }else if ( ( !password_et.getText().toString().equals("")) ){
				                    Toast.makeText(getApplicationContext(),
				                            "Please enter your email", Toast.LENGTH_SHORT).show();
				                }else {
				                    Toast.makeText(getApplicationContext(),
				                            "Username and Password field are empty", Toast.LENGTH_SHORT).show();
				                }
					      }else{
					    	  Toast.makeText(getApplicationContext(),
			                            "No network connection", Toast.LENGTH_SHORT).show();
					      }	
					}
				});
	    
	    reset_btn.setOnClickListener(
	    		new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
	    		});
	    
	    signup_btn.setOnClickListener(
	    		new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent signup=new Intent(getApplicationContext(),SignupActivity.class);
						startActivity(signup);					
					}
	    		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
