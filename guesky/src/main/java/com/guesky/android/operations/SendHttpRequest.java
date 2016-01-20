package com.guesky.android.operations;

import com.guesky.android.config.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SendHttpRequest {
	
	URL signupURL;
	String response;
		
	public SendHttpRequest() throws MalformedURLException{
		signupURL = new URL(AppConfig.AUTHENTICATION_URL);
	}
	
	public String getResponse(String data) throws IOException{
		HttpURLConnection connection;
		connection = (HttpURLConnection) signupURL.openConnection();
		connection.setDoOutput(true);
		OutputStreamWriter Request = new OutputStreamWriter(connection.getOutputStream());
		Request.write(data);
		Request.flush(); 
		
		InputStreamReader rawResponse = new InputStreamReader(connection.getInputStream());
		BufferedReader reader = new BufferedReader(rawResponse);
		StringBuilder stringbuilder = new StringBuilder();
        String line = null;
       
        while((line = reader.readLine()) != null)
        {
        	stringbuilder.append(line);
        	break;
        }
        
		response = stringbuilder.toString();		
		return response;
	}
}
