package com.team28.borrow.di.module;

import com.team28.borrow.presentation.feature.detail.fragment.DetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailFragmentBuilder {
    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();


}
