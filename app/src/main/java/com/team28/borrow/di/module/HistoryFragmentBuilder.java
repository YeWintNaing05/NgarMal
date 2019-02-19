package com.team28.borrow.di.module;

import com.team28.borrow.presentation.feature.history.HistoryFragment;
import com.team28.borrow.presentation.feature.history.SubHistoryBorrowItemsFragment;
import com.team28.borrow.presentation.feature.history.SubHistoryPostItemFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract class HistoryFragmentBuilder {

    @ContributesAndroidInjector
    abstract HistoryFragment contributeHistoryFragment();


    @ContributesAndroidInjector
    abstract SubHistoryPostItemFragment contributeSubHistoryFragment();

    @ContributesAndroidInjector
    abstract SubHistoryBorrowItemsFragment contributeSubHistoryBorrowItemsFragment();

}
