package com.ecdriver.v1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashActivity extends Activity {
	
	 // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;
    static boolean firstboot;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        new Handler().postDelayed(new Runnable() {
            /**
             * This splash page will run for 5 seconds
             */
            @Override
            public void run() {
            	
            	 // This method will be executed once the timer is over
                // Start your app main activity
                Context context = getApplicationContext();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                if(!prefs.getBoolean("firstTime", false)) {
                    // run your one time code here
                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(i);

                }

                     firstboot = getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getBoolean("firstboot", true);
//
                    if (firstboot){
//                      // 1) Launch the authentication activity
                        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(i);   
//                      // 2) Then save the state
                        getSharedPreferences("BOOT_PREF", MODE_PRIVATE)
                            .edit()
                            .putBoolean("firstboot", false)
                            .commit();
                    }

                else
                {
                    Intent i = new Intent(getApplicationContext(), DashBoard.class);
                    startActivity(i);
                }
//                Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
//                startActivity(i);
                finish(); // closing this activity
            }
        }, SPLASH_TIME_OUT);
    }
 

}
