package com.team28.borrow.data.repository;

import com.team28.borrow.data.cache.PreferenceHelper;
import com.team28.borrow.data.datasource.contact.mapper.ContactDataEntityMapper;
import com.team28.borrow.data.datasource.contact.network.NetworkContactDataSource;
import com.team28.borrow.domain.model.Contact;
import com.team28.borrow.domain.repository.ContactRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ContactRepositoryImpl implements ContactRepository {


    private final NetworkContactDataSource mNetworkContactDataSource;
    private final ContactDataEntityMapper mContactDataEntityMapper;
    private final PreferenceHelper preferenceHelper;

    @Inject
    ContactRepositoryImpl(NetworkContactDataSource networkContactDataSource, ContactDataEntityMapper mContactDataEntityMapper, PreferenceHelper preferenceHelper) {
        this.mNetworkContactDataSource = networkContactDataSource;
        this.mContactDataEntityMapper = mContactDataEntityMapper;
        this.preferenceHelper = preferenceHelper;
    }


    @Override
    public Completable addContact(String uuid, Contact contact) {
        preferenceHelper.setPhoneNum(contact.phone);
        preferenceHelper.setUserName(contact.name);

        return Completable.fromAction(() -> mNetworkContactDataSource.addContact(uuid, mContactDataEntityMapper.reverses(contact)));
    }

    @Override
    public Single<Contact> getContactById(String user_id) {
        return mNetworkContactDataSource.getContactById(user_id).map(mContactDataEntityMapper::map);
    }
}
