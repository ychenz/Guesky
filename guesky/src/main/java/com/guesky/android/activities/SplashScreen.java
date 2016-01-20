package com.guesky.android.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.guesky.android.R;

public class SplashScreen extends Activity{

	private View splashView;
	private TextView splashText;
	private int fadeoutAnimDuration;
	private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		//set font
		splashText = (TextView)findViewById(R.id.splash_view);
		Typeface splashTypeface = Typeface.createFromAsset(getAssets(),"fonts/Peas_Carrots.ttf");
		splashText.setTypeface(splashTypeface);
		
		//prepare animation
		fadeoutAnimDuration = 1500;
		splashView = splashText;
		
		//start animation
		splashView.setAlpha(0f);
		splashView.animate()
        		  .alpha(1f)
        		  .setDuration(fadeoutAnimDuration)
        		  .setListener(null);
		
		//start login event after 2s
        mHandler.postDelayed(new Runnable() {
            public void run() {
            	startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            	finish();
            }
        }, 2000);
	
	}
	
	
	
}
