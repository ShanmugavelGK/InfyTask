package com.gkshanmugavel.newapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.gkshanmugavel.newapp.databinding.ActivitySplashBinding;
import com.gkshanmugavel.newapp.view.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    private int WAIT_MILLISECONDS = 1000;
    private Context mContext;

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        mContext = this;

        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(mContext, HomeActivity.class));
                    ActivityCompat.finishAffinity(SplashActivity.this);
                }
            }, WAIT_MILLISECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
