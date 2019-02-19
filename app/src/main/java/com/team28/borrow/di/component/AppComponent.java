package com.team28.borrow.di.component;

import com.team28.borrow.SamsungApp;
import com.team28.borrow.di.module.ActivityBuilder;
import com.team28.borrow.di.module.AppModule;
import com.team28.borrow.di.module.PreferenceModule;
import com.team28.borrow.di.module.RepositoryModule;
import com.team28.borrow.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class,
        ActivityBuilder.class, RepositoryModule.class,ViewModelModule.class,PreferenceModule.class})
public interface AppComponent {

    void inject(SamsungApp app);

    @Component.Builder interface Builder {
        @BindsInstance
        Builder application(SamsungApp application);

        AppComponent build();
    }
}