package org.fhi360.ddd.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import org.fhi360.ddd.R;

public class SplashScreen extends AppCompatActivity {
    static int TIMEOUT_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, TIMEOUT_MILLIS);
    }

///api/ddd/mobile/pharmacy

}
