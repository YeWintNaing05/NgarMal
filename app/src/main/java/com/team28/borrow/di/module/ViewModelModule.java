package com.team28.borrow.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.team28.borrow.presentation.feature.borrow.BorrowFormViewModel;
import com.team28.borrow.presentation.feature.main.noti.NotiViewModel;
import com.team28.borrow.presentation.feature.owner_post.OwnerFormViewModel;
import com.team28.borrow.presentation.feature.item_list.CategoryItemListViewModel;
import com.team28.borrow.presentation.feature.history.HistoryViewModel;
import com.team28.borrow.presentation.feature.main.home.HomeViewModel;
import com.team28.borrow.presentation.feature.signin.ContactViewModel;
import com.team28.borrow.presentation.viewmodel.ModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(OwnerFormViewModel.class)
    abstract ViewModel bindUsersViewModel(OwnerFormViewModel ownerFormViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryItemListViewModel.class)
    abstract ViewModel bindCategoryItemListViewModel(CategoryItemListViewModel categoryItemListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryItemsViewModel(HistoryViewModel categoryItemListViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(BorrowFormViewModel.class)
    abstract ViewModel bindHistoryBorrowFormViewModel(BorrowFormViewModel borrowFormViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotiViewModel.class)
    abstract ViewModel bindHistoryNotiViewModel(NotiViewModel notiViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContactViewModel.class)
    abstract ViewModel bindContactViewModel(ContactViewModel notiViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindProviderFactory(ModelProviderFactory providerFactory);

}