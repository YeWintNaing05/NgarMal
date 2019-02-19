package com.team28.borrow.presentation.feature.signin;

import com.team28.borrow.domain.usecase.AddContact;
import com.team28.borrow.presentation.base.BaseViewModel;
import com.team28.borrow.presentation.mapper.ContactModelMapper;
import com.team28.borrow.presentation.model.ContactModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class ContactViewModel extends BaseViewModel {

    private AddContact addContact;
    private final ContactModelMapper modelMapper;


    private BehaviorSubject<String> contactDataSubject = BehaviorSubject.create();

    @Inject
    ContactViewModel(AddContact addContact, ContactModelMapper modelMapper) {
        this.addContact = addContact;
        this.modelMapper = modelMapper;
    }


    public void addContact(ContactModel contactModel, String uuid) {
        add(addContact.execute(new AddContact.Action(uuid, modelMapper.reverses(contactModel))).subscribe(() -> contactDataSubject.onNext("Complete "), this::showError));
    }

    private void showError(Throwable throwable) {
        contactDataSubject.onNext(throwable.getMessage());
    }


    public Observable<String> addContactStream() {
        return contactDataSubject;
    }
}
