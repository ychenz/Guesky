package com.guesky.android.operations;

import android.content.Context;
import android.util.Log;

import com.guesky.android.config.AppConfig;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;
import java.util.HashMap;

public class XMPPHandler {
	
	Context context;
	ConnectionConfiguration config;
	
	public XMPPHandler(Context context){
		this.context = context;
		SmackAndroid.init(context);
		config = new ConnectionConfiguration(AppConfig.XMPP_HOST, AppConfig.XMPP_PORT);
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
	}
	
	public boolean userLogin(int uid,String password) throws XMPPException, SmackException, IOException{
		XMPPConnection connection = new XMPPTCPConnection(config);
		String id = String.valueOf(uid);
		
		connection.connect();
		connection.login(id,password);
		Presence presence = new Presence(Presence.Type.available);
		connection.sendPacket(presence);
		return true;
	}
	
	//connecting XMPP without security mode, testing method
	public boolean createAccount(int uid,String email,String password,String name) throws IOException{
		HashMap<String, String> userInfo = new HashMap<String, String>();
		String id;
		String username;
		
		//create xmpp IM account during signup process
        XMPPConnection connection = new XMPPTCPConnection(config);
        
        try {
			connection.connect();
			connection.login(AppConfig.XMPP_ADMIN_USERNAME, AppConfig.XMPP_ADMIN_PASSWORD);
			AccountManager createUser = AccountManager.getInstance(connection);
			id = String.valueOf(uid);
			
			userInfo.put("name", name);
			userInfo.put("email",email);
			
			createUser.createAccount(id, password,userInfo);
			return true;
			
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			StackTraceElement[] trace = new Exception().getStackTrace();
			Log.d("Guesky SmackException", trace.toString(),e);
			return false;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			StackTraceElement[] trace = new Exception().getStackTrace();
			Log.d("Guesky XMPPException", trace.toString(),e);
			return false;
		}
	}

}
