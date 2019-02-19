package com.team28.borrow.domain.usecase;

import com.team28.borrow.domain.executor.PostExecutionThread;
import com.team28.borrow.domain.executor.ThreadExecutor;
import com.team28.borrow.domain.model.Contact;
import com.team28.borrow.domain.repository.ContactRepository;
import com.team28.borrow.domain.usecase.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddContact extends CompletableUseCase<AddContact.Action> {

    private ContactRepository contactRepository;


    @Inject
    public AddContact(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ContactRepository contactRepository) {
        super(threadExecutor, postExecutionThread);
        this.contactRepository = contactRepository;
    }

    @Override
    protected Completable executeInternal(Action action) {
        return contactRepository.addContact(action.uuid, action.contact);
    }

    public static class Action {
        public String uuid;
        public Contact contact;

        public Action(String uuid, Contact contact) {
            this.uuid = uuid;
            this.contact = contact;
        }
    }
}
