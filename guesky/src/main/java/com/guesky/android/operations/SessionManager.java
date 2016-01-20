package com.guesky.android.operations;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	
	private static final String USER_SESSION = "userSession";
	private static final String USERNAME = "username";
	private static final String USER_EMAIL = "email";
	private static final String USER_ID = "id";
	private static final String LOGIN_COUNT = "login_count";
	private static final String IS_LOGGEDIN = "isLoggedIn";
	private static final String VISIBILITY = "visibility";
	
	private SharedPreferences session;
	private Context _context;
	private Editor sessionEditor;

	public SessionManager(Context context){
		this._context=context;
		session = _context.getSharedPreferences(USER_SESSION, Context.MODE_PRIVATE);
		sessionEditor = session.edit();
	}
	
	public void startSession(String userEmail,int id,int login_count,int visibility,String username){	
		sessionEditor.putString(USER_EMAIL, userEmail);
		sessionEditor.putString(USERNAME, username);
		sessionEditor.putInt(USER_ID, id);
		sessionEditor.putInt(LOGIN_COUNT, login_count);
		sessionEditor.putInt(VISIBILITY, visibility);
		sessionEditor.putBoolean(IS_LOGGEDIN, true);
		sessionEditor.commit();
	}
	
	public String getUserEmail(){
		return session.getString(USER_EMAIL, null);
	}
	
	public int getUserId(){
		return session.getInt(USER_ID, 0);
	}
	
	public int getLoginCount(){
		return session.getInt(LOGIN_COUNT, 0);
	}
	
	public boolean checkLogin(){
		return session.getBoolean(IS_LOGGEDIN, false);
	}
	
	public void destroySession(){
		sessionEditor.clear();
	}

	public void setLoginCount(int i) {
		sessionEditor.putInt(LOGIN_COUNT, i);
		sessionEditor.commit();	
	}
	
	public void setLoginStatus(Boolean i) {
		sessionEditor.putBoolean(IS_LOGGEDIN, i);
		sessionEditor.commit();	
	}
}
