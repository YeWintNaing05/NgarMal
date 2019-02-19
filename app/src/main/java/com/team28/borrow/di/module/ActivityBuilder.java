package com.team28.borrow.di.module;

import com.team28.borrow.presentation.feature.borrow.BorrowFormActivity;
import com.team28.borrow.presentation.feature.history.HistoryActivity;
import com.team28.borrow.presentation.feature.owner_post.OwnerFormActivity;
import com.team28.borrow.presentation.feature.detail.DetailActivity;
import com.team28.borrow.presentation.feature.item_list.CategoryItemListActivity;
import com.team28.borrow.presentation.feature.main.MainActivity;
import com.team28.borrow.presentation.feature.signin.SigninActivity;
import com.team28.borrow.presentation.feature.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainFragmentBuilder.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = SigninFragmentBuilder.class)
    abstract SigninActivity contributeSigninActivity();

    @ContributesAndroidInjector
    abstract OwnerFormActivity contributeOwnerFormActivity();

    @ContributesAndroidInjector
    abstract BorrowFormActivity contributeBorrowFormActivity();

    @ContributesAndroidInjector
    abstract CategoryItemListActivity contributeCategoryItemListActivity();

    @ContributesAndroidInjector(modules = DetailFragmentBuilder.class)
    abstract DetailActivity contributeDetailActivity();

    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector(modules = HistoryFragmentBuilder.class)
    abstract HistoryActivity contributeHistoryActivity();


   /* @ContributesAndroidInjector(modules = MainFragmentBuilder.class)
    abstract TestActivity contributeTestActivity();
*/

}
