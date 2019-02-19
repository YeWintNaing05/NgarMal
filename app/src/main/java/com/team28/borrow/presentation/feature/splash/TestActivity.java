package com.team28.borrow.presentation.feature.splash;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.feature.main.MainFragmentPagerAdapter;
import com.team28.borrow.presentation.feature.main.category.CategoryFragment;
import com.team28.borrow.presentation.feature.history.HistoryFragment;
import com.team28.borrow.presentation.feature.main.home.HomeFragment;
import com.team28.borrow.presentation.feature.main.noti.NotiFragment;
import com.team28.borrow.presentation.feature.owner_post.OwnerFormActivity;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class TestActivity extends BaseActivity implements HasSupportFragmentInjector {


    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_category,
            R.drawable.ic_notifications_black_24dp,
            R.drawable.ic_restore_black_24dp
    };

    @Inject
    DispatchingAndroidInjector<Fragment> injector;


    @Override
    protected void main(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> OwnerFormActivity.start(this));


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabIcons() {


        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(this.getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "ONE");
        adapter.addFrag(new CategoryFragment(), "TWO");
        adapter.addFrag(new NotiFragment(), "THREE");
        adapter.addFrag(new HistoryFragment(), "FOUR");
        viewPager.setAdapter(adapter);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }

}