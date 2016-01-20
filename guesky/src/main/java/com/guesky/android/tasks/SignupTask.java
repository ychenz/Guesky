package com.guesky.android.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.guesky.android.operations.SendHttpRequest;
import com.guesky.android.operations.XMPPHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class SignupTask extends AsyncTask<String,String,Boolean>{

	private ProgressDialog nDialog;
	private JSONObject signupResult =null;
	private Context context;
	private Activity activity;
	private int errorCode;
	
	public SignupTask(Context context,Activity activity){
		this.context = context;
		this.activity = activity;
	}
	
	@Override
	protected void onPreExecute(){
        super.onPreExecute();
        nDialog = new ProgressDialog(this.context);
        nDialog.setTitle("Processing");
        nDialog.setMessage("We are signing you up..");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
	}
	
	@Override
	protected Boolean doInBackground(String... signupInfo) {
		String email= signupInfo[0];
		String password= signupInfo[1]; 
		String username= signupInfo[2];
		String response;
		
		try {
			String request = "email=" + URLEncoder.encode(email,"UTF-8") +
					"&password="+ URLEncoder.encode(password,"UTF-8") +
					"&username="+ URLEncoder.encode(username,"UTF-8") +
					"&tag="  + URLEncoder.encode("signup","UTF-8")+"&";
			SendHttpRequest send = new SendHttpRequest();
			response = send.getResponse(request);
			signupResult = new JSONObject(response);
			
			if(signupResult.getInt("success")==1){
				int uid = signupResult.getInt("id");
				XMPPHandler IMSignup = new XMPPHandler(this.context);
				if(IMSignup.createAccount(uid,email, password, username)){
					return true;
				}else{
					errorCode = 3;
					return false;
				}
			}else{
				errorCode = signupResult.getInt("error");
				return false;
			}
					
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			Toast.makeText(context,"Sign up success and welcome!", Toast.LENGTH_SHORT).show();
			activity.finish();			
		}else{
			switch(errorCode){
				case 1:
					nDialog.dismiss();
					Toast.makeText(context,"Sign up failed", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					nDialog.dismiss();
					Toast.makeText(context,"Sorry, this email already been used", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					nDialog.dismiss();
					Toast.makeText(context,"Failed to register IM account", Toast.LENGTH_SHORT).show();
					
			}
		}
	}

}
