package com.example.mia.loop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

/**
 * Created by mia on 4/15/15.
 */
public class Splash extends Activity {
    // Duration of wait
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    // Called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // New handler to start the menu-activity and close this splash screen after some seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an intent that will start the menu-activity
                Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }

        }, SPLASH_DISPLAY_LENGTH);
    }
}
