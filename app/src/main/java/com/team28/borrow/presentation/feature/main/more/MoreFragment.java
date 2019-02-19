package com.team28.borrow.presentation.feature.main.more;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.rxbinding2.view.RxView;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseFragment;
import com.team28.borrow.presentation.feature.history.HistoryActivity;
import com.team28.borrow.presentation.feature.splash.SplashActivity;
import com.team28.borrow.util.Constants;
import com.team28.borrow.util.DialogUtils;
import com.team28.borrow.util.custom_view.MMTextView;

import java.util.Objects;

import butterknife.BindView;

public class MoreFragment extends BaseFragment {


    @BindView(R.id.fbLayout)
    RelativeLayout fbLayout;

    @BindView(R.id.aboutLayout)
    RelativeLayout aboutLayout;

    @BindView(R.id.historyLayout)
    RelativeLayout historyLayout;

    @BindView(R.id.shareLayout)
    RelativeLayout shareLayout;

    @BindView(R.id.logoutLayout)
    RelativeLayout logoutLayout;

    @BindView(R.id.txtUserName)
    MMTextView txtUserName;

    @BindView(R.id.txtPhoneNum)
    MMTextView txtPhoneNum;

    private SharedPreferences mSharePreference;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_more;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharePreference = Objects.requireNonNull(getContext()).getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);


        txtUserName.setText(mSharePreference.getString(Constants.PREF_KEY_NAME, ""));
        txtPhoneNum.setText(mSharePreference.getString(Constants.PREF_KEY_PHONE, ""));

        startSubscription();
    }

    private void startSubscription() {
        add(RxView.clicks(fbLayout).subscribe(ignored -> changeFb()));
        add(RxView.clicks(historyLayout).subscribe(ignored -> changeHistory()));
        add(RxView.clicks(aboutLayout).subscribe(ignored -> changeAbout()));
        add(RxView.clicks(shareLayout).subscribe(ignored -> changeShare()));
        add(RxView.clicks(logoutLayout).subscribe(ignored -> logout()));
    }

    private void changeAbout() {
        Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.setContentView(R.layout.about_us);

        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.findViewById(R.id.btnOk).setOnClickListener(v -> dialog.hide());


        dialog.show();


    }

    private void logout() {
        DialogUtils.showMessageOKCancel(getContext(), "Are your sure  want to logout?", (dialog, which) -> {
            FirebaseAuth.getInstance().signOut();
            mSharePreference.edit().clear().apply();
            SplashActivity.start(getContext());
        });
    }

    private Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/194776194677158"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ငွါးမယ္-194776194677158"));
        }
    }

    private void changeShare() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=com.team28.borrow");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void changeHistory() {
        HistoryActivity.start(getContext());
    }

    private void changeFb() {
        startActivity(getOpenFacebookIntent(getContext()));
    }


}
