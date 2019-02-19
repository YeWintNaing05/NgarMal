package com.team28.borrow.di.module;

import com.team28.borrow.data.datasource.borrow_form_data.network.NetworkBorrowFormDataSource;
import com.team28.borrow.data.datasource.borrow_form_data.network.NetworkBorrowFormDataSourceImpl;
import com.team28.borrow.data.datasource.contact.network.NetworkContactDataSource;
import com.team28.borrow.data.datasource.contact.network.NetworkContactDataSourceImpl;
import com.team28.borrow.data.datasource.item.network.NetworkItemDataSource;
import com.team28.borrow.data.datasource.item.network.NetworkItemDataSourceImpl;
import com.team28.borrow.data.datasource.noti.network.NetworkNotiDataSource;
import com.team28.borrow.data.datasource.noti.network.NetworkNotiDataSourceImpl;
import com.team28.borrow.data.repository.BorrowRepositoryImpl;
import com.team28.borrow.data.repository.ContactRepositoryImpl;
import com.team28.borrow.data.repository.NotiRepositoryImpl;
import com.team28.borrow.data.repository.PostItemRepositoryImpl;
import com.team28.borrow.domain.repository.BorrowRepository;
import com.team28.borrow.domain.repository.ContactRepository;
import com.team28.borrow.domain.repository.NotiRepository;
import com.team28.borrow.domain.repository.PostItemRepository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {


    @Binds
    abstract PostItemRepository bindPostItemRepository(PostItemRepositoryImpl userRepository);


    @Binds
    abstract NetworkItemDataSource bindNetworkItemDataSource(NetworkItemDataSourceImpl networkUserDataSource);

    @Binds
    abstract BorrowRepository bindBorrowRepository(BorrowRepositoryImpl userRepository);


    @Binds
    abstract NetworkBorrowFormDataSource bindNetworkBorrowFormmDataSource(NetworkBorrowFormDataSourceImpl networkUserDataSource);

    @Binds
    abstract NotiRepository bindNotiRepository(NotiRepositoryImpl notiRepository);


    @Binds
    abstract NetworkNotiDataSource bindNetworkNotiDataSource(NetworkNotiDataSourceImpl networkNotiDataSource);

    @Binds
    abstract ContactRepository bindContactRepository(ContactRepositoryImpl contactRepository);


    @Binds
    abstract NetworkContactDataSource bindNetworkContactDataSource(NetworkContactDataSourceImpl networkContactDataSource);


}