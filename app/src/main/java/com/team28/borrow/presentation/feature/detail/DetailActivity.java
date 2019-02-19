package com.team28.borrow.presentation.feature.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.team28.borrow.R;
import com.team28.borrow.presentation.base.BaseActivity;
import com.team28.borrow.presentation.feature.detail.fragment.DetailFragment;
import com.team28.borrow.presentation.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailActivity extends BaseActivity implements HasSupportFragmentInjector {


    @Inject
    DispatchingAndroidInjector<Fragment> injector;

    @BindView(R.id.viewpager)
    ViewPager viewPager;


    public static void start(Context context, ArrayList<ItemModel> listToJson, int itemPos) {
        Intent starter = new Intent(context, DetailActivity.class);
        starter.putExtra("pos", itemPos);
        starter.putParcelableArrayListExtra("data", listToJson);
        context.startActivity(starter);
    }


    @Override
    protected void main(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int itemPos = 0;
        List<ItemModel> modelList = new ArrayList<>();

        if (getIntent().hasExtra("data") && getIntent().hasExtra("pos")) {
            modelList = getIntent().getParcelableArrayListExtra("data");
            itemPos = getIntent().getIntExtra("pos", 0);
        }


        setupViewPager(viewPager, modelList, itemPos);

    }

    private void setupViewPager(ViewPager viewPager, List<ItemModel> modelList, int itemPos) {
        DetilFragmentPagerAdapter adapter = new DetilFragmentPagerAdapter(this.getSupportFragmentManager());

        for (ItemModel model : modelList) {
            adapter.addFrag(DetailFragment.newInstance(model));
        }

        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(itemPos);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
