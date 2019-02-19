package com.team28.borrow.di.module;

import com.team28.borrow.presentation.feature.main.category.CategoryFragment;
import com.team28.borrow.presentation.feature.history.HistoryFragment;
import com.team28.borrow.presentation.feature.main.more.MoreFragment;
import com.team28.borrow.presentation.feature.history.SubHistoryBorrowItemsFragment;
import com.team28.borrow.presentation.feature.history.SubHistoryPostItemFragment;
import com.team28.borrow.presentation.feature.main.home.HomeFragment;
import com.team28.borrow.presentation.feature.main.noti.NotiFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract NotiFragment contributeSubNotiFragment();


    @ContributesAndroidInjector
    abstract CategoryFragment contributeCategoryFragment();

    @ContributesAndroidInjector
    abstract MoreFragment contributeMoreFragment();


}
