package com.team28.borrow.di.module;

import android.content.Context;

import com.team28.borrow.SamsungApp;
import com.team28.borrow.data.executor.JobExecutor;
import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.presentation.executor.UiThread;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Provides
    @Singleton
    static Context provideContext(SamsungApp application) {
        return application;
    }

    @Binds
    abstract PostExecutionThread bindPostExecutionThread(UiThread uiThread);

    @Binds
    abstract ThreadExecutor provideThreadExecutor(JobExecutor jobsExecutor);
}