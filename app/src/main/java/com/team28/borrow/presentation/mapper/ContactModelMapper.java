package com.team28.borrow.presentation.mapper;

import android.content.Context;

import com.team28.borrow.data.model.entity.ContactDataEntity;
import com.team28.borrow.domain.model.Contact;
import com.team28.borrow.presentation.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ContactModelMapper extends ViewStateMapper<ContactModel, Contact> {


    @Inject
    public ContactModelMapper(Context context) {
        super(context);
    }

    public List<ContactModel> map(List<Contact> entities) {
        List<ContactModel> contacts = new ArrayList<>();
        for (Contact entity :
                entities) {
            contacts.add(new ContactModel(entity.name, entity.phone));
        }

        return contacts;
    }

    public Contact reverses(ContactModel contact) {
        return new Contact(contact.name, contact.phone);
    }

    @Override
    public ContactModel map(Contact domainModel) {
        return new ContactModel(domainModel.name, domainModel.phone);
    }
}
