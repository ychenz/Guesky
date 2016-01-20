package com.guesky.android.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.guesky.android.activities.Main;
import com.guesky.android.config.AppConfig;
import com.guesky.android.operations.DatabaseHandler;
import com.guesky.android.operations.SendHttpRequest;
import com.guesky.android.operations.SessionManager;
import com.guesky.android.operations.XMPPHandler;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginTask extends AsyncTask<String,String,Boolean>{
	
	private ProgressDialog nDialog;
	private Context context;
	private Activity activity;
	private int errorCode;
	private int attempt = 0;  //# of attempts left after login failed
	private SessionManager session;
	private SQLiteDatabase user_details;
	
	public LoginTask(Context currentContext,Activity activity,SessionManager session) {
		this.context = currentContext;
		this.activity = activity;
		this.session = session;
	}
	
	@Override
	protected void onPreExecute(){
        super.onPreExecute();
        nDialog = new ProgressDialog(this.context);
        nDialog.setTitle("Logging in");
        nDialog.setMessage("Just a sec..");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
	}

	@Override
	protected Boolean doInBackground(String... userData) {
		
		String email=userData[0];
		String password=userData[1]; 
		String tag="login";
		String mURL = AppConfig.AUTHENTICATION_URL;//web API url
		String response=null;		
		InputStream res = null;	
			
		try {
			String request = "email=" + URLEncoder.encode(email,"UTF-8") +
					"&password="+ URLEncoder.encode(password,"UTF-8") +
					"&tag="  + URLEncoder.encode("login","UTF-8")+"&";
			
			SendHttpRequest loginProcess = new SendHttpRequest();
			response = loginProcess.getResponse(request);
			JSONObject userAuthentication = new JSONObject(response);
			            
			if( userAuthentication.getInt("success") == 1 ){
					
				int uid = userAuthentication.getInt("id");
				int loginCount = userAuthentication.getInt("login_count");
				int visibility = userAuthentication.getInt("visibility");
				String username = userAuthentication.getString("username");
				
				//XMPP login
				XMPPHandler xlogin = new XMPPHandler(this.context);
				if(xlogin.userLogin(uid,password)){		
					
					//start login session
					session.startSession(email,uid,loginCount,visibility,username);
					
					//prepare database
					DatabaseHandler database = new DatabaseHandler(this.context, "guesky_user_details");
					database.addColumn("user_id", "integer");
					database.addColumn("gender", "integer");
					database.addColumn("birthday", "integer");
					database.addColumn("home_address", "text");
					database.addColumn("present_address", "text");
					database.addColumn("career", "text");
					database.addColumn("company", "text");
					database.addColumn("hobbies", "text");
					user_details = database.getWritableDatabase();
						
					return true;
				}else{
					errorCode = 4;
					return false;
				}
			}else{
				
				errorCode = userAuthentication.getInt("error");
					
				if(errorCode == 3){
					attempt = userAuthentication.getInt("login_attempt");
				}	
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			StackTraceElement[] trace = new Exception().getStackTrace();
			Log.d("Guesky XMPPException", trace.toString(),e);
			errorCode = 4;
		} catch (SmackException e) {
			StackTraceElement[] trace = new Exception().getStackTrace();
			Log.d("Guesky SmackException", trace.toString(),e);
			errorCode = 4;
		}
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		nDialog.dismiss();
		if(!result){
			switch(errorCode){
				case 4:
					Toast.makeText(this.context,"XMPP login failed", Toast.LENGTH_SHORT).show();
				case 3:
					Toast.makeText(this.context,"Invalide password "+attempt+" attempts left", Toast.LENGTH_SHORT).show();
					break;
				case 2:
			        Toast.makeText(this.context,"Account Locked", Toast.LENGTH_SHORT).show();
			        break;
				default:
			         Toast.makeText(this.context,"User not found", Toast.LENGTH_SHORT).show();
			}
		}else{
			activity.startActivity(new Intent(this.context,Main.class));
			activity.finish();
		}		
	}
}
