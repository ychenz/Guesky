package com.guesky.android.activities.mainFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guesky.android.R;

public class ContactListAdapter extends BaseAdapter{
		
		int[] portraits;
		String[] names;
		String[] lastMessages;
		int itemCount;
		LayoutInflater mInflater;
		Context context;
		
		public ContactListAdapter(int[] portraits,String[] names, String[] lastMessages, Context context){
			this.context = context;
			this.portraits = portraits;
			this.names = names;
			this.lastMessages = lastMessages;
			this.itemCount = names.length;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.itemCount;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = new ViewHolder();

			if (convertView == null){
	            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	            convertView = mInflater.inflate(R.layout.contact_item, parent, false);
	            
	            holder.portrait=(ImageView)convertView.findViewById(R.id.portrait);
	            holder.name = (TextView) convertView.findViewById(R.id.name);
	            holder.message = (TextView) convertView.findViewById(R.id.last_message);            
	            
	            convertView.setTag(holder);
		    } else {
		        holder = (ViewHolder) convertView.getTag();
		    }
			
			holder.portrait.setImageResource(portraits[position]);
            holder.name.setText(names[position]);
            holder.message.setText(lastMessages[position]);
            
			return convertView;
		}
		
		static class ViewHolder {

			private ImageView portrait;
			private TextView name;
			private TextView message;

		}
		
}
