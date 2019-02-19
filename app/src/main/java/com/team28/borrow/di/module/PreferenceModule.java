package com.team28.borrow.di.module;

import com.team28.borrow.data.cache.PreferenceHelper;
import com.team28.borrow.data.cache.PreferenceHelperImpl;
import com.team28.borrow.di.PerferenceInfo;
import com.team28.borrow.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferenceModule {

    @Provides
    @PerferenceInfo
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(PreferenceHelperImpl appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
