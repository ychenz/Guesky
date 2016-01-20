package com.guesky.android.operations;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class JSONParser {
	
	private String url;
	private ArrayList data;
	
	private String json = null;	
	private InputStream res = null;		
	private JSONObject jObj =null;
	
	public JSONParser(ArrayList data, String url){
		this.data = data;
		this.url = url;
	}

	public JSONObject getJSON(){
		try {
			DefaultHttpClient httpClient=new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
		    httpPost.setEntity(new UrlEncodedFormEntity(data));
		    HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
		    res = httpEntity.getContent();
		            
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		                
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(res));              
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		                
		    // Read Server Response
		    while((line = reader.readLine()) != null){
		         sb.append(line);
		    }
		    
		    res.close();
		    json=sb.toString();
		    
		    if(json == null)
		    	return null;
		    
		    Log.e("JSON", json);
		    
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result (JSONParser.class)" + e.toString());
		}
		           
		//parse json
		try {
			jObj = new JSONObject(json);
		     
		} catch (JSONException e) {
		    Log.e("JSON Parser", "Error parsing data (JSONParser.class) " + e.toString());
		}
		
		return jObj;
	}
	
}
