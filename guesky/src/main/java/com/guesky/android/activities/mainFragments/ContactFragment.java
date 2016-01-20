package com.guesky.android.activities.mainFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.guesky.android.R;

public class ContactFragment extends Fragment{	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//test item
		int[] portraits ={R.drawable.ic_launcher,R.drawable.ic_launcher};
		String[] names={"user1","user2"};
	    String[] messages={"Message1","Message2"};
		
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.main_contacts, container, false);
		ListView recList = (ListView) rootView.findViewById(R.id.contactList);
		recList.setAdapter(new ContactListAdapter(portraits,names,messages,getActivity()));		
	   
		return rootView;
	}
}
