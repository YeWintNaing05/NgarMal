package com.team28.borrow.data.datasource.contact.network;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.team28.borrow.data.model.entity.ContactDataEntity;
import com.team28.borrow.domain.model.Contact;
import com.team28.borrow.rxfirebase.RxFirestore;

import javax.inject.Inject;

import io.reactivex.Single;

public class NetworkContactDataSourceImpl implements NetworkContactDataSource {


    private FirebaseFirestore mFirebaseDb;



    @Inject
    NetworkContactDataSourceImpl() {
        mFirebaseDb = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .setPersistenceEnabled(true)
                .build();
        mFirebaseDb.setFirestoreSettings(settings);

    }


    @Override
    public void addContact(String uuid, ContactDataEntity contact) {
        RxFirestore.addDocument(mFirebaseDb.collection("contact").document(uuid), contact).subscribe();

    }

    @Override
    public Single<ContactDataEntity> getContactById(String user_id) {
        return RxFirestore.getDocument(mFirebaseDb.collection("contact").document(user_id)).toSingle().map(documentSnapshot -> documentSnapshot.toObject(ContactDataEntity.class));
    }
}
