package com.guesky.android.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.guesky.android.R;
import com.guesky.android.activities.firstLogin.LoginShow;
import com.guesky.android.activities.mainFragments.ContactFragment;
import com.guesky.android.activities.mainFragments.ProfileFragment;
import com.guesky.android.operations.SessionManager;

public class Main extends Activity {
	
	private static final int NUM_PAGES = 2;

	private ViewPager mainViewPager;
	private PagerAdapter mainViewPagerAdapter;
	private SessionManager session;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createToolBar();
		
		mainViewPager = (ViewPager) findViewById(R.id.main_pager);
		mainViewPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		mainViewPager.setAdapter(mainViewPagerAdapter);
		
		session = new SessionManager(getApplicationContext());
		int loginCount = session.getLoginCount();
		
		if(!session.checkLogin()){
			startActivity(new Intent(getApplicationContext(),LoginActivity.class));
			finish();
		}else if(loginCount == 1){
			session.setLoginCount(2);
			startActivity(new Intent(getApplicationContext(),LoginShow.class));
		}
	}
	
	private void createToolBar(){
		Toolbar navBarBottom = (Toolbar) findViewById(R.id.main_toolbar);
		navBarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
	        @Override
	        public boolean onMenuItemClick(MenuItem item) {
	            switch(item.getItemId()){
	                case R.id.option_profile:
	                    // TODO
	                    break;
	                // TODO: Other cases
	            }
	            return true;
	        }
	    });
		navBarBottom.inflateMenu(R.menu.navigation);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (mainViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
        	mainViewPager.setCurrentItem(mainViewPager.getCurrentItem() - 1);
        }
	}

	
	public void logout(){
		session.destroySession();
	}
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0){
				return new ProfileFragment();
			}else {
				return new ContactFragment();
			}		
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	
	}
}
