package com.guesky.android.operations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static String databaseName;
	private ArrayList fieldNames;
	private ArrayList fieldTypes;

	public DatabaseHandler(Context context, String databaseName) {
		super(context, databaseName, null, 1, null);
		DatabaseHandler.databaseName = databaseName;
		fieldNames = new ArrayList<String>();
		fieldTypes = new ArrayList<String>();
	}
	

	public void addColumn(String fieldName, String fieldType){
		fieldNames.add(fieldName);
		fieldTypes.add(fieldType);	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//create statement
		String query = "create table if not exists " + databaseName
						+ " ( id integer primary key autoincrement not null";
		
		for(int i=0;i<fieldNames.size();i++){
			query = query + ", " + fieldNames.get(i) + " " + fieldTypes.get(i);
		}
		query = query + ");";
		Log.d("SQLite query",query);
		
		db.execSQL(query);
		fieldNames.clear();
		fieldTypes.clear();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub 
		
	}

}
