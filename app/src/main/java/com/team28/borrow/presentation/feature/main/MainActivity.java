package com.team28.borrow.presentation.feature.main;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.rxbinding2.view.RxView;
import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.feature.main.category.CategoryFragment;
import com.team28.borrow.presentation.feature.main.more.MoreFragment;
import com.team28.borrow.presentation.feature.main.home.HomeFragment;
import com.team28.borrow.presentation.feature.main.noti.NotiFragment;
import com.team28.borrow.presentation.feature.main.noti.NotiViewModel;
import com.team28.borrow.presentation.feature.owner_post.OwnerFormActivity;
import com.team28.borrow.presentation.model.NotiModel;
import com.team28.borrow.presentation.sevice.NetworkSchedulerService;
import com.team28.borrow.util.Constants;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MainActivity extends BaseActivity implements HasSupportFragmentInjector {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }


    @Inject
    ViewModelProvider.Factory factory;

    private NotiViewModel notiViewModel;


    @Inject
    DispatchingAndroidInjector<Fragment> injector;

    @BindView(R.id.navigation)
    AHBottomNavigation navigation;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    SharedPreferences sharedPreferences;


    private AHBottomNavigation.OnTabSelectedListener mOnNavigationItemSelectedListener = (position, wasSelected) -> {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.instantiate(MainActivity.this, HomeFragment.class.getName());
                break;
            case 1:
                fragment = CategoryFragment.instantiate(MainActivity.this, CategoryFragment.class.getName());
                break;
            case 2:
                sharedPreferences.edit().putInt("noti", 0).apply();
                navigation.setNotification("", 2);
                fragment = NotiFragment.instantiate(MainActivity.this, NotiFragment.class.getName());
                break;
            case 3:
                fragment = MoreFragment.instantiate(MainActivity.this, MoreFragment.class.getName());
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
        }
        return true;
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        stopService(new Intent(this, NetworkSchedulerService.class));
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        Intent startServiceIntent = new Intent(this, NetworkSchedulerService.class);
        startService(startServiceIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }


    @Override
    protected void main(Bundle savedInstanceState) {


        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.navigation);
        navigationAdapter.setupWithBottomNavigation(navigation, new int[]{getResources().getColor(R.color.colorPrimary)});


        navigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        navigation.setTranslucentNavigationEnabled(true);


        navigation.setOnTabSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);


        notiViewModel = ViewModelProviders.of(this, factory).get(NotiViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, HomeFragment.instantiate(this, HomeFragment.class.getName())).commit();
            notiViewModel.getAddedNoti(FirebaseAuth.getInstance().getCurrentUser().getUid());

        }
        startSubscription();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scheduleJob();
        }


    }


    private void startSubscription() {


        add(RxView.clicks(floatingActionButton).subscribe(ignored -> OwnerFormActivity.start(this)));
        add(notiViewModel.notiAddedItemStream().subscribe(this::render));


    }

    private void render(NotiModel notiModel) {
        if (notiModel != null) {
            sharedPreferences.edit().putInt("noti", sharedPreferences.getInt("noti", 0) + 1).apply();
            navigation.setNotification(String.valueOf(sharedPreferences.getInt("noti", 0)), 2);
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }

}
