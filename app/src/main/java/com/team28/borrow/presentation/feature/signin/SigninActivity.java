package com.team28.borrow.presentation.feature.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.feature.main.MainActivity;
import com.team28.borrow.presentation.feature.signin.event.SendAction;
import com.team28.borrow.presentation.feature.signin.fragment.SendPhoneNumFragment;
import com.team28.borrow.presentation.feature.signin.fragment.VerificationPhoneFragment;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class SigninActivity extends BaseActivity implements HasSupportFragmentInjector, SendAction {


    @Inject
    DispatchingAndroidInjector<Fragment> injector;


    @BindView(R.id.txtOne)
    TextView txtOne;

    @BindView(R.id.txtTwo)
    TextView txtTwo;

    FirebaseAuth mAuth;

    public static void start(Context context) {
        Intent starter = new Intent(context, SigninActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onStart() {
        super.onStart();
     /*   mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            MainActivity.start(this);
        }
*/

    }

    @Override
    protected void main(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, SendPhoneNumFragment.instantiate(this, SendPhoneNumFragment.class.getName())).commit();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_signin;
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }

    @Override
    public void clickNextBtn(String phone, String name) {

        if (Build.VERSION.SDK_INT < 23) {
            txtOne.setTextAppearance(getApplicationContext(), R.style.normalText);
            txtTwo.setTextAppearance(getApplicationContext(), R.style.boldText);
        } else {
            txtOne.setTextAppearance(R.style.normalText);
            txtTwo.setTextAppearance(R.style.boldText);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, VerificationPhoneFragment.newInstance(this, phone, name)).commit();


    }

    @Override
    public void clickVerifyBtn() {

    }
}
