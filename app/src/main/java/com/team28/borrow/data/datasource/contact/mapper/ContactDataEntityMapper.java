package com.team28.borrow.data.datasource.contact.mapper;

import com.team28.borrow.data.datasource.Mapper;
import com.team28.borrow.data.model.entity.ContactDataEntity;
import com.team28.borrow.domain.model.Contact;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ContactDataEntityMapper extends Mapper<Contact, ContactDataEntity> {


    @Inject
    ContactDataEntityMapper() {

    }

    @Override
    public Contact map(ContactDataEntity entity) {
        return new Contact(entity.name, entity.phone);
    }

    public List<Contact> map(List<ContactDataEntity> entities) {
        List<Contact> contacts = new ArrayList<>();
        for (ContactDataEntity entity :
                entities) {
            contacts.add(new Contact(entity.name, entity.phone));
        }

        return contacts;
    }

    public ContactDataEntity reverses(Contact contact) {
        return new ContactDataEntity(contact.name, contact.phone);
    }
}
