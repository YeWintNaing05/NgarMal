package com.team28.borrow.data.datasource.contact.network;

import com.team28.borrow.data.model.entity.ContactDataEntity;
import com.team28.borrow.domain.model.Contact;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface NetworkContactDataSource {

    void addContact(@NonNull String uuid,ContactDataEntity
            contact);

    Single<ContactDataEntity> getContactById(@NonNull String user_id);


}
