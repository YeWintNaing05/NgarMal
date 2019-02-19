package com.team28.borrow.domain.repository;

import com.team28.borrow.domain.model.Contact;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface ContactRepository {
    Completable addContact(@NonNull String uuid,
                           Contact contact);

    Single<Contact> getContactById(@NonNull String user_id);


}
