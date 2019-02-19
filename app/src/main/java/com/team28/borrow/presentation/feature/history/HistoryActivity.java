package com.team28.borrow.presentation.feature.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.feature.borrow.BorrowFormActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HistoryActivity extends BaseActivity implements HasSupportFragmentInjector {


    @Inject
    DispatchingAndroidInjector<Fragment> injector;

    public static void start(Context context) {
        Intent starter = new Intent(context, HistoryActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, String borrow) {
        Intent starter = new Intent(context, HistoryActivity.class);
        starter.putExtra("type", borrow);
        context.startActivity(starter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void main(Bundle savedInstanceState) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null)
            if (getIntent().hasExtra("type"))
                getSupportFragmentManager().beginTransaction().replace(R.id.content, HistoryFragment.newInstance(getIntent().getStringExtra("type"))).commit();
            else
                getSupportFragmentManager().beginTransaction().replace(R.id.content, HistoryFragment.newInstance("owner")).commit();


    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
