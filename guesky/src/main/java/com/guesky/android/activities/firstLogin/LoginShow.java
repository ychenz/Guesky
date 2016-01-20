package com.guesky.android.activities.firstLogin;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guesky.android.R;

public class LoginShow extends FragmentActivity {
	
	private static final int NUM_PAGES = 3;
	private ViewPager showPager;
	private PagerAdapter showPagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0); 
		setContentView(R.layout.login_show);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		showPager = (ViewPager)findViewById(R.id.pager);
		showPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		showPager.setAdapter(showPagerAdapter);	
	}

    @Override
    public void onBackPressed() {
        if (showPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
        	showPager.setCurrentItem(showPager.getCurrentItem() - 1);
        }
    }
    
    //onClick method
	public void startActivity(View v){
		finish();
	}
    
    private class LoginShowFragment extends Fragment{
    	
    	private int PageNumber;
    	
    	public LoginShowFragment(int position){
    		PageNumber = position+1;
    	}
    	
    	@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
    		
    		ViewGroup rootView = null;
    		
    		if ( PageNumber == 1){
	            rootView = (ViewGroup) inflater.inflate(
	                    R.layout.login_show_page1, container, false);
    		}else if( PageNumber == 2){
    			rootView = (ViewGroup) inflater.inflate(
	                    R.layout.login_show_page2, container, false);
    		}else if( PageNumber == 3){
    			rootView = (ViewGroup) inflater.inflate(
	                    R.layout.login_show_page3, container, false);
    		}
            return rootView;
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return new LoginShowFragment(position);
		}
    }
}
