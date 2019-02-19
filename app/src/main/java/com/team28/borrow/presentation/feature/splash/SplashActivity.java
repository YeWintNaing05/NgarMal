package com.team28.borrow.presentation.feature.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.feature.main.MainActivity;
import com.team28.borrow.presentation.feature.signin.SigninActivity;

public class SplashActivity extends BaseActivity {


    private static final long SPLASH_DISPLAY_LENGTH = 1500;


    public static void start(Context context) {
        Intent starter = new Intent(context, SplashActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void main(Bundle savedInstanceState) {

        new Handler().postDelayed(() -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                SigninActivity.start(this);
                finish();
            } else {
                MainActivity.start(this);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}
